# deploy script for the web front-end

# This file is responsible for preprocessing all TypeScript files, making sure
# all dependencies are up-to-date, and copying all necessary files into the
# web deploy directory.

# This is the resource folder where maven expects to find our files
TARGETFOLDER=../backend/src/main/java/edu/lehigh/cse216/tbt220/backend

# This is the folder that we used with the Spark.staticFileLocation command
WEBFOLDERNAME=dist

# step 1: make sure we have someplace to put everything.  We will delete the
#         old folder tree, and then make it from scratch
rm -rf $TARGETFOLDER
mkdir $TARGETFOLDER
mkdir $TARGETFOLDER/$WEBFOLDERNAME

# step 2: update our npm dependencies
npm update

# there are many more steps to be done.  For now, we will just copy an HTML file
cp dist/index.html $TARGETFOLDER/$WEBFOLDERNAME

# step 3: copy jQuery, Handlebars, and Bootstrap files
cp node_modules/jquery/dist/jquery.min.js $TARGETFOLDER/$WEBFOLDERNAME
cp node_modules/handlebars/dist/handlebars.min.js $TARGETFOLDER/$WEBFOLDERNAME
#cp node $TARGETFOLDER/$WEBFOLDERNAME
