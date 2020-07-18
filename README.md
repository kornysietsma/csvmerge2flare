**ARCHIVED** this is part of my older code tools - see https://kornysietsma.github.io/polyglot-tools-docs/ for latest stuff.

# csvmerge2flare

A Clojure library designed to merge csv files such as those from code-maat with a flare nested json file.

I mostly use this for [code-maat](https://github.com/adamtornhill/code-maat) output processing,
but I made it slightly general purpose so you can use it for other csv files if needed.

This is one component in the tools I use to produce my [toxic code explorer visualisation](https://github.com/kornysietsma/toxic-code-explorer-demo)

## Usage

The easiest way to run this without needing clojure is to use an uberjar -
a bundled jar file of the program and all dependencies (including clojure).

You can download a `csvmerge2flare.jar` file from this project's releases page
at https://github.com/kornysietsma/csvmerge2flare/releases

Then you can run
`java -jar csvmerge2flare.jar -h` for help, it will produce something like this:

```
Usage: java -jar csvmerge2flare.jar [options]

Options:
  -b, --base filename            select a base flare-format json file for merging - if you don't specify a -b option, the program will try to read flare data from standard input for piping
  -i, --input filename           select an input csv file name
  -c, --category cat             specify the category for the input data - data will be stored in 'node.data.<category>' in the flare JSON structure.
  -f, --field f          entity  specify which field in a csv file represents the filename - defaults to 'entity' for code-maat files
  -r, --root r                   specify root in the flare file to merge to, e.g. 'src/java' to assume csv is relative to this dir
  -o, --output filename          select an output file name (default is STDOUT)
  -h, --help
```

Input consists of two files - a base flare file, as used in d3 - this might well be the output of [cloc2flare](https://github.com/kornysietsma/cloc2flare)
and will look something like this:

```
{
  "name" : "flare",
  "children" : [ {
    "name" : "project.clj",
    "data" : {}
    }, {
    "name" : "modules",
    "children" : [ {
```

The second imput file is a csv file, similar to the output of [code-maat](https://github.com/adamtornhill/code-maat):

```
entity,age-months
project.clj,1
modules/incanter-svg/project.clj,1
modules/incanter-charts/project.clj,1
modules/incanter-charts/test/incanter/charts_tests.clj,1
```

If you run `csv2merge` as follows:

`java -jar csv2merge.json -b base-flare.json -i code-maat-age.csv -c code-maat -f entity`

you will get a flare JSON file, in this example something like:

```
{
  "name" : "flare",
  "children" : [ {
    "name" : "project.clj",
    "data" : {
      "code-maat" : {
        "age-months" : "1"
      }
    }
  }, {
```

## License

Copyright © 2018 Kornelis Sietsma

Licensed under the Apache License, Version 2.0 - see LICENSE.txt for details
