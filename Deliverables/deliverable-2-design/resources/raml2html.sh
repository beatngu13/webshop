#!/bin/bash

for file in *.raml;
do
  raml2html $file > ${file%.*}.html
done
