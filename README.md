[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h2 align="center">tinyEncoder 
</h2>
  <p align="center">An implementation of the jpeg file compression encoding algorithm
    <br />
    <a href="https://github.com/domanjie/tinyEncoder/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/domanjie/tinyEncoder/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->

## About The Project

An implementation of JPEG compression Algorithm which produces a compressed jpeg file from uncompressed bitmap pixel arrays. Presently, this encoder can only produce jpeg images with the Baseline sequential DCT(see the specification below) procedure from 24 and 32 bpp(bit per pixel) bitmap files.

<!-- GETTING STARTED -->

## Getting Started

### Prerequisites

- JDK 17
- Apache Maven build tool

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/domanjie/tinyEncoder.git
   ```
2. enter into project root directory
   ```sh
   cd ./tinyEncoder
   ```
3. install project dependencies
   ```sh
   mvn dependency:resolve
   ```
4. create an executable jar file
   ```sh
   mvn package
   ```
   The executable jar file gets added to the _target_ directory in the projects root directory.

## Usage

To use the encoder, locate the jar file in the target directory and run and path to a sample bimap image.

```sh
java -jar ./target/tinyEncoder.jar  sampleImage.bmp
```

## TODO(Features)

- [ ] add support for other bitmap image types( 8bpp,ones with icc profiles,etc)
- [ ] Add extensive cli for interacting with the encoder.
- [ ] Add support for concurrent encoding;

See the [open issues](https://github.com/domanjie/tinyEncoder/issues) for a full list of proposed features (and known issues).

<!-- CONTRIBUTING -->

## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

### Top contributors:

<a href="https://github.com/domanjie/tinyEncoder/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=domanjie/tinyEncoder" alt="contrib.rocks image" />
</a>

<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- CONTACT -->

## Contact

Domanjie - [@domanjie](https://twitter.com/domanjie) - domanjiel@gmail.com

Project Link: [https://github.com/domanjie/tinyEncoder](https://github.com/your_username/repo_name)

## Resources

- [JPEG File Compression Algorithm Specification](https://w3.org/Graphics/JPEG/itu-t81.pdf)
- [JFIF(JPEG File Interchange Format Specification)](https://www.webpagefx.com/tools/emoji-cheat-sheet)
- [Bitmap file format](https://en.wikipedia.org/wiki/BMP_file_format)
<!-- MARKDOWN LINKS & IMAGES -->

[contributors-shield]: https://img.shields.io/github/contributors/domanjie/tinyEncoder.svg?style=for-the-badge
[contributors-url]: https://github.com/domanjie/tinyEncoder/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/domanjie/tinyEncoder.svg?style=for-the-badge
[forks-url]: https://github.com/domanjie/tinyEncoder/network/members
[stars-shield]: https://img.shields.io/github/stars/domanjie/tinyEncoder.svg?style=for-the-badge
[stars-url]: https://github.com/domanjie/tinyEncoder/stargazers
[issues-shield]: https://img.shields.io/github/issues/domanjie/tinyEncoder.svg?style=for-the-badge
[issues-url]: https://github.com/domanjie/tinyEncoder/issues
[license-shield]: https://img.shields.io/github/license/domanjie/tinyEncoder.svg?style=for-the-badge
[license-url]: https://github.com/domanjie/tinyEncoder/blob/main/LICENSE.txt

