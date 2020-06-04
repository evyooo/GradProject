import os
import sys
import json
from flask import Flask, jsonify, request, render_template, redirect, url_for
from werkzeug.utils import secure_filename

from conn import Mysql

app = Flask('api')

#  아이디 중복 확인
@app.route("/check_id")
def check_id():
    con = Mysql()
    userid = request.args.get("userid")

    query = "select * from USERINFO where userid = '" + userid + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    if (query_result):
        result = {'result': 1}
    else:
        result = {'result': -1, 'message':"unvalid"}

    con.close()
    return jsonify(result)


#  회원가입
@app.route("/register")
def register():
    con = Mysql()
    name = request.args.get("name")
    userid = request.args.get("userid")
    userpw = request.args.get("userpw")
    birthday = request.args.get("birthday")

    query = """insert into USERINFO (name, userid, userpw, birthday) 
                    values (%s, %s, %s, %s)"""
    con.cursor.execute(query, (name, userid, userpw, birthday))
    con.db.commit()
    con.close()
    return jsonify({'result': 1})


#  로그인
@app.route("/login")
def login():
    con = Mysql()
    userid = request.args.get("userid")
    userpw = request.args.get("userpw")

    query = "select * from USERINFO where userid = '" + userid + "' and userpw = '" + userpw + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    if (query_result):
        result = {'result': 1}
    else:
        result = {'result': -1, 'message': "unvalid"}

    con.close()
    return jsonify(result)



#  새로운 방 생성
@app.route("/create_room")
def create_room():
    con = Mysql()
    roomtitle = request.args.get("roomtitle")
    roomdes = request.args.get("roomdes")
    user1 = request.args.get("user1")

    query = """insert into ROOMINFO (roomtitle, roomdes, user1) 
                    values (%s, %s, %s)"""
    con.cursor.execute(query, (roomtitle, roomdes, user1))
    con.db.commit()
    con.close()
    return jsonify({'result': 1})



#  방 갯수 세기
@app.route("/count_room")
def count_room():
    con = Mysql()
    userid = request.args.get("userid")

    query = "select count(roomindex) from ROOMINFO where user1 = '" + userid + "' or user2 = '" + userid + "' or user3 = '" + userid + "' or user4 = '" + userid + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    if (query_result):
        result = {'result': 1, 'count': query_result[0][0]}
    else:
        result = {'result': -1, 'message': "unvalid"}

    con.close()
    return jsonify(result)






app.run(host='0.0.0.0', port = 5000)
