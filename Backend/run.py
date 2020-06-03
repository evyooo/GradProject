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





app.run(host='0.0.0.0', port = 5000)
