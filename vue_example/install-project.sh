###
#   This script should only be used once when project is first cloned
#   NOTE: Will not work if dependencies are added or versions are updated
#         For these changes, you must manually run `npm install` in the local
#         directory being updated
###

### Root-level dependencies ###
# Checks to make sure the node_modules folder exists
npm install

### Client Dependencies ###
cd ./client
npm install

### Server Dependencies ###
cd ../server
npm install

# return to root level
cd ..