import subprocess
import sys
import os
import argparse

parser = argparse.ArgumentParser(description='Tool for generating documentation.')
parser.add_argument('doclet', type=str, help='The main class of the doclet')
parser.add_argument('sourceDir', type=str, help='The directory of the project to document')
parser.add_argument('-a', type=str, nargs='*', help='Additional jars; tools.jar should be one of them.')

args = parser.parse_args()

jars = ''
if args.a:
    for jar in args.a:
        jars += ':' + jar

javadoc = [
    'javadoc', 
    '-cp', 
    jars, 
    '-doclet',
    'com.atherys.doclet.MarkdownDoclet',
    '-docletpath', 
    args.doclet
]

for root, dirs, files in os.walk(args.sourceDir):
    for file in files:
        if file.endswith('java'):
            javadoc.append(os.path.join(root, file))

subprocess.call(javadoc)
