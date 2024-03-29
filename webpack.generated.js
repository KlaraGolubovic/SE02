/**
 * NOTICE: this is an auto-generated file
 *
 * This file has been generated by the `flow:prepare-frontend` maven goal.
 * This file will be overwritten on every run. Any custom changes should be made to webpack.config.js
 */
const fs = require('fs');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const CompressionPlugin = require('compression-webpack-plugin');
const { BabelMultiTargetPlugin } = require('webpack-babel-multi-target-plugin');

const path = require('path');
const baseDir = path.resolve(__dirname);
// the folder of app resources (main.js and flow templates)
const frontendFolder = require('path').resolve(__dirname, 'frontend');

const fileNameOfTheFlowGeneratedMainEntryPoint = require('path').resolve(__dirname, 'target/frontend/generated-flow-imports.js');
const mavenOutputFolderForFlowBundledFiles = require('path').resolve(__dirname, 'target/classes/META-INF/VAADIN');

const devmodeGizmoJS = '@vaadin/flow-frontend/VaadinDevmodeGizmo.js'

// public path for resources, must match Flow VAADIN_BUILD
const build = 'build';
// public path for resources, must match the request used in flow to get the /build/stats.json file
const config = 'config';
// folder for outputting index.js bundle, etc.
const buildFolder = `${mavenOutputFolderForFlowBundledFiles}/${build}`;
// folder for outputting stats.json
const confFolder = `${mavenOutputFolderForFlowBundledFiles}/${config}`;
// file which is used by flow to read templates for server `@Id` binding
const statsFile = `${confFolder}/stats.json`;
// make sure that build folder exists before outputting anything
const mkdirp = require('mkdirp');

const devMode = process.argv.find(v => v.indexOf('webpack-dev-server') >= 0);

!devMode && mkdirp(buildFolder);
mkdirp(confFolder);

let stats;

const transpile = !devMode || process.argv.find(v => v.indexOf('--transpile-es5') >= 0);

const watchDogPrefix = '--watchDogPort=';
let watchDogPort = devMode && process.argv.find(v => v.indexOf(watchDogPrefix) >= 0);
let client;
if (watchDogPort) {
  watchDogPort = watchDogPort.substr(watchDogPrefix.length);
  const runWatchDog = () => {
    client = new require('net').Socket();
    client.setEncoding('utf8');
    client.on('error', function () {
      console.log("Watchdog connection error. Terminating webpack process...");
      client.destroy();
      process.exit(0);
    });
    client.on('close', function () {
      client.destroy();
      runWatchDog();
    });

    client.connect(watchDogPort, 'localhost');
  }

  runWatchDog();
}

exports = {
  frontendFolder: `${frontendFolder}`,
  buildFolder: `${buildFolder}`,
  confFolder: `${confFolder}`
};

module.exports = {
  mode: 'production',
  context: frontendFolder,
  entry: {
    bundle: fileNameOfTheFlowGeneratedMainEntryPoint,
    ...(devMode && { gizmo: devmodeGizmoJS })
  },

  output: {
    filename: `${build}/vaadin-[name]-[contenthash].cache.js`,
    path: mavenOutputFolderForFlowBundledFiles,
    publicPath: 'VAADIN/',
  },

  resolve: {
    alias: {
      Frontend: frontendFolder
    }
  },

  devServer: {
    // webpack-dev-server serves ./ ,  webpack-generated,  and java webapp
    contentBase: [mavenOutputFolderForFlowBundledFiles, 'src/main/webapp'],
    after: function (app, server) {
      app.get(`/stats.json`, function (req, res) {
        res.json(stats);
      });
      app.get(`/stats.hash`, function (req, res) {
        res.json(stats.hash.toString());
      });
      app.get(`/assetsByChunkName`, function (req, res) {
        res.json(stats.assetsByChunkName);
      });
      app.get(`/stop`, function (req, res) {
        // eslint-disable-next-line no-console
        console.log("Stopped 'webpack-dev-server'");
        process.exit(0);
      });
    }
  },

  module: {
    rules: [
      ...(transpile ? [{ // Files that Babel has to transpile
        test: /\.js$/,
        use: [BabelMultiTargetPlugin.loader()]
      }] : []),
      {
        test: /\.css$/i,
        use: ['raw-loader']
      }
    ]
  },
  performance: {
    maxEntrypointSize: 2097152, // 2MB
    maxAssetSize: 2097152 // 2MB
  },
  plugins: [
    // Generate compressed bundles when not devMode
    ...(devMode ? [] : [new CompressionPlugin()]),

    // Transpile with babel, and produce different bundles per browser
    ...(transpile ? [new BabelMultiTargetPlugin({
      babel: {
        plugins: [
          // workaround for Safari 10 scope issue (https://bugs.webkit.org/show_bug.cgi?id=159270)
          "@babel/plugin-transform-block-scoping",

          // Edge does not support spread '...' syntax in object literals (#7321)
          "@babel/plugin-proposal-object-rest-spread"
        ],

        presetOptions: {
          useBuiltIns: false // polyfills are provided from webcomponents-loader.js
        }
      },
      targets: {
        'es6': { // Evergreen browsers
          browsers: [
            // It guarantees that babel outputs pure es6 in bundle and in stats.json
            // In the case of browsers no supporting certain feature it will be
            // covered by the webcomponents-loader.js
            'last 1 Chrome major versions'
          ],
        },
        'es5': { // IE11
          browsers: [
            'ie 11'
          ],
          tagAssetsWithKey: true, // append a suffix to the file name
        }
      }
    })] : []),

    // Generates the stats file for flow `@Id` binding.
    function (compiler) {
      compiler.hooks.afterEmit.tapAsync("FlowIdPlugin", (compilation, done) => {
        let statsJson = compilation.getStats().toJson();
        // Get bundles as accepted keys (except any es5 bundle)
        let acceptedKeys = statsJson.assets.filter(asset => asset.chunks.length > 0 && !asset.chunkNames.toString().includes("es5"))
          .map(asset => asset.chunks).reduce((acc, val) => acc.concat(val), []);

        // Collect all modules for the given keys
        const modules = collectModules(statsJson, acceptedKeys);

        // Collect accepted chunks and their modules
        const chunks = collectChunks(statsJson, acceptedKeys);

        let customStats = {
          hash: statsJson.hash,
          assetsByChunkName: statsJson.assetsByChunkName,
          chunks: chunks,
          modules: modules
        };

        if (!devMode) {
          // eslint-disable-next-line no-console
          console.log("         Emitted " + statsFile);
          fs.writeFile(statsFile, JSON.stringify(customStats, null, 1), done);
        } else {
          // eslint-disable-next-line no-console
          console.log("         Serving the 'stats.json' file dynamically.");

          stats = customStats;
          done();
        }
      });

      compiler.hooks.done.tapAsync('FlowIdPlugin', (compilation, done) => {
        // trigger live reload via server
        if (client) {
          client.write('reload\n');
        }
        done();
      });
    },

    // Copy webcomponents polyfills. They are not bundled because they
    // have its own loader based on browser quirks.
    new CopyWebpackPlugin([{
      from: `${baseDir}/node_modules/@webcomponents/webcomponentsjs`,
      to: `${build}/webcomponentsjs/`
    }]),
  ]
};

/**
 * Collect chunk data for accepted chunk ids.
 * @param statsJson full stats.json content
 * @param acceptedKeys chunk ids that are accepted
 * @returns slimmed down chunks
 */
function collectChunks(statsJson, acceptedChunks) {
  const chunks = [];
  // only handle chunks if they exist for stats
  if (statsJson.chunks) {
    statsJson.chunks.forEach(function (chunk) {
      // Acc chunk if chunk id is in accepted chunks
      if (acceptedChunks.includes(chunk.id)) {
        const modules = [];
        // Add all modules for chunk as slimmed down modules
        chunk.modules.forEach(function (module) {
          const slimModule = {
            id: module.id,
            name: module.name,
            source: module.source,
          };
          modules.push(slimModule);
        });
        const slimChunk = {
          id: chunk.id,
          names: chunk.names,
          files: chunk.files,
          hash: chunk.hash,
          modules: modules
        }
        chunks.push(slimChunk);
      }
    });
  }
  return chunks;
}

/**
 * Collect all modules that are for a chunk in  acceptedChunks.
 * @param statsJson full stats.json
 * @param acceptedChunks chunk names that are accepted for modules
 * @returns slimmed down modules
 */
function collectModules(statsJson, acceptedChunks) {
  let modules = [];
  // skip if no modules defined
  if (statsJson.modules) {
    statsJson.modules.forEach(function (module) {
      // Add module if module chunks contain an accepted chunk and the module is generated-flow-imports.js module
      if (module.chunks.filter(key => acceptedChunks.includes(key)).length > 0
        && (module.name.includes("generated-flow-imports.js") || module.name.includes("generated-flow-imports-fallback.js"))) {
        let subModules = [];
        // Create sub modules only if they are available
        if (module.modules) {
          module.modules.filter(module => !module.name.includes("es5")).forEach(function (module) {
            const subModule = {
              name: module.name,
              source: module.source
            };
            subModules.push(subModule);
          });
        }
        const slimModule = {
          id: module.id,
          name: module.name,
          source: module.source,
          modules: subModules
        };
        modules.push(slimModule);
      }
    });
  }
  return modules;
}
