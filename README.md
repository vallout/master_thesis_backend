# Codebase for the UI of the master thesis "A Gamified Design of a Collaborative Project-based Platform"

# Usage instructions


Clone the repository locally with:
`git clone https://github.com/vallout/master_thesis_backend.git`

First, the MongoDB has to be initialized. The easiest way to do this is by using docker and python3 (most versions should work):

`cd master_thesis_backend`\
`docker-compose up -d`\
`pip install pymongo`\
`python init_replicaset.py`\

This will start the database with all relevant settings.

The backend has been run from a current version of eclipse (2020). To start the application, open eclipse and chose: \
"File > Import... > Existing Maven Projects > Browse... > path/to/dir > Finish"

Now, the application can be started from the IDE.