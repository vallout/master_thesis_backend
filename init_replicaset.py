from pymongo import MongoClient

c = MongoClient("localhost:27017")
c.admin.command("replSetInitiate")