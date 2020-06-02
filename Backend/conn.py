import pymysql

class Mysql(object):
    def __init__(self):
        self.connect()

    def connect(self):
        self.db = pymysql.connect(host="13.124.180.27", user="root", passwd="1234", db="Android", charset="utf8mb4")
        self.cursor = self.db.cursor()

    def close(self):
        self.db.close()

