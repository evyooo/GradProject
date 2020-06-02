import os
import sys
import json
from flask import Flask, jsonify, request, render_template, redirect, url_for
from werkzeug.utils import secure_filename

from conn import Mysql

app = Flask('api')
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024


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


app.run(host='0.0.0.0', port = 5000)

