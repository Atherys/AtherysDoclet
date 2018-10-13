import subprocess
import sys
import os

jars = ''
for i in range(1, len(sys.argv)-2):
    jars += ':' + sys.argv[i]

javadoc = [
    'javadoc', 
    '-cp', 
    '/var/lib/jenkins/resources/tools.jar:/var/lib/jenkins/resources/spongeapi.jar' + jars, 
    '-doclet',
    'MarkdownDoclet',
    '-docletpath', 
    sys.argv[len(sys.argv)-2]
]

target = sys.argv[len(sys.argv)-1]
for root, dirs, files in os.walk(target):
    for file in files:
        if file.endswith('java'):
            javadoc.append(os.path.join(root, file))

subprocess.call(javadoc)
