# Changelog

## 2.0.0 - 2021-01-03
### Changed
- Updated to VDF 2.0
    - VDF 1.0 files are no longer supported
    - All element types can be used as top-level elements
- 'BinaryIO' and 'TextIO' renamed to 'BinaryVDF' and 'TextVDF'
  - Also, many wrapper functions were added in these classes
- Added parse(File) and write(File, boolean) to 'VDFObject' and 'VDFList'

## 1.0.0 - 2020-12-29
- First stable release
