// module.exports = {
//     testEnvironment: 'jsdom',
//     setupFilesAfterEnv: ['@testing-library/jest-dom/extend-expect'],
//     moduleDirectories: ['node_modules', 'src'],
//     transform: {
//         "^.+\\.(js|jsx)$": "babel-jest"
//       },
//       transformIgnorePatterns: [
//         "/node_modules/(?!(axios)/)"
//       ],
//       moduleFileExtensions: ["js", "jsx"],
//   };
  
// jest.config.js

module.exports = {
    transformIgnorePatterns: [
      "/node_modules/(?!(axios|other-package-to-transform)/)"
    ],
    transform: {
      "^.+\\.(js|jsx)$": "babel-jest"
    },
    moduleNameMapper: {
      "\\.(css|less|scss|sass)$": "identity-obj-proxy"
    },
  };