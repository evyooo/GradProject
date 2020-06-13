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

    query = "insert into ROOMINFO (roomtitle, roomdes, user1, membercount) values ('" + roomtitle + "', '" + roomdes + "','" + user1 + "', '1')"
    con.cursor.execute(query)
    con.db.commit()

    check = "select roomindex from ROOMINFO where roomtitle = '" + roomtitle + "' and roomdes = '" + roomdes + "' and user1 ='" + user1 + "' order by roomindex DESC LIMIT 1"
    con.cursor.execute(check)
    cq = con.cursor.fetchall()

    roomindex = cq[0][0]

    updatequery = "update USERINFO set lastroom = '" + str(roomindex) + "' where userid = '" + user1 + "'"
    con.cursor.execute(updatequery)
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



#  방 정보 가져오기 ing
@app.route("/get_roominfo")
def get_roominfo():
    con = Mysql()
    roomindex = request.args.get("roomindex")

    query = "select * from ROOMINFO where roomindex = '" + roomindex + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    names = ""

    for i in range(int(query_result[0][7])):
        namequery = "select name from USERINFO where userid = '" + query_result[0][i+3] + "'"
        con.cursor.execute(namequery)
        namequery_result = con.cursor.fetchall()
        names = names + (namequery_result[0][0]) + ","


    if (query_result):
        result = {'result': 1, 'title': query_result[0][1], 'des': query_result[0][2], 'users': names, 'membercount': query_result[0][7]}
    else:
        result = {'result': -1, 'message': "unvalid"}

    con.close()
    return jsonify(result)


@app.route("/update_roominfo")
def update_roominfo():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    roomtitle = request.args.get("title")
    roomdes = request.args.get("des")

    query = "update ROOMINFO set roomtitle = '" + roomtitle + "', roomdes = '" + roomdes + "' where roomindex = '" + roomindex + "'"
    con.cursor.execute(query)
    con.db.commit()
    con.close()
    return jsonify({'result': 1})



#  마지막 접속 방 인덱스
@app.route("/bring_roomindex")
def bring_roomindex():
    con = Mysql()
    userid = request.args.get("userid")

    query = "select lastroom from USERINFO where userid = '" + userid + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    if (query_result):
        result = {'result': 1, 'index': query_result[0][0]}
    else:
        result = {'result': -1, 'message': "unvalid"}

    con.close()
    return jsonify(result)


#  마지막 접속 방 추가
@app.route("/put_roomindex")
def put_roomindex():
    con = Mysql()
    userid = request.args.get("userid")
    index = request.args.get("index")

    query = "update USERINFO set lastroom = '" + index + "' where userid = '" + userid + "'"
    con.cursor.execute(query)
    con.db.commit()
    con.close()
    return jsonify({'result': 1})


@app.route("/get_userinfo")
def get_userinfo():
    con = Mysql()
    userid = request.args.get("userid")

    query = "select name, birthday from USERINFO where userid = '" + userid + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    if (query_result):
        result = {'result': 1, 'name': query_result[0][0], 'birthday': query_result[0][1]}
    else:
        result = {'result': -1}

    con.close()
    return jsonify(result)


#  개인정보 수정 후 저장
@app.route("/update_userinfo")
def update_userinfo():
    con = Mysql()
    userid = request.args.get("userid")
    name = request.args.get("name")
    userpw = request.args.get("userpw")
    birthday = request.args.get("birthday")

    query = "update USERINFO set name = '" + name + "', userpw = '" + userpw + "', birthday = '" + birthday + "' where userid = '" + userid + "'"
    con.cursor.execute(query)
    con.db.commit()
    con.close()
    return jsonify({'result': 1})


@app.route("/get_rooms")
def get_rooms():
    con = Mysql()
    userid = request.args.get("userid")

    query = "select roomindex, roomtitle from ROOMINFO where user1 = '" + userid + "' or user2 = '" + userid + "' or user3 = '" + userid + "' or user4 = '" + userid + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    roominfo = []

    for row in query_result:
        roominfo.append({"index": row[0], 'title': row[1]})

    if (query_result):
        result = {'result': 1, 'roominfo': roominfo}
    else:
        result = {'result': -1}

    con.close()
    return jsonify(result)



#  초대 코드 가져오기
@app.route("/bring_randomcode")
def bring_randomcode():
    con = Mysql()
    roomindex = request.args.get("roomindex")

    checkquery = "select * from INVITESTRING where roomindex= '" + roomindex + "'"
    con.cursor.execute(checkquery)
    query_result = con.cursor.fetchall()

    #  비어있으면 생성
    if not query_result:
        query = "insert into INVITESTRING (roomindex, randomstring) values ('" + roomindex + "', left(MD5('" + roomindex + "'),9))"
        con.cursor.execute(query)
        con.db.commit()

    checkquery = "select randomstring from INVITESTRING where roomindex= '" + roomindex + "'"
    con.cursor.execute(checkquery)
    finalresult = con.cursor.fetchall()

    if (finalresult):
        result = {'result': 1, 'randomstring': finalresult[0][0]}
    else:
        result = {'result': -1}

    con.close()
    return jsonify(result)


#  초대 코드로 방 가입
@app.route("/join_room")
def join_room():
    con = Mysql()
    randomstring = request.args.get("randomstring")
    userid = request.args.get("userid")

    checkquery = "select roomindex from INVITESTRING where randomstring= '" + randomstring + "'"
    con.cursor.execute(checkquery)
    query_result = con.cursor.fetchall()

    roomindex = query_result[0][0]

    # randomstring이랑 같은 인덱스를 찾으면
    if query_result:
        checkquery1 = "select membercount from ROOMINFO where roomindex = '" + roomindex + "'"
        con.cursor.execute(checkquery1)
        query_result1 = con.cursor.fetchall()

        membercount = query_result1[0][0]

        if membercount == 0:
            query = "update ROOMINFO set user1 = '" + userid + "', membercount = 1 where roomindex = '" + roomindex + "'"
            result = {"result": 1, "roomindex": roomindex}

            updatequery = "update USERINFO set lastroom = '" + roomindex + "' where userid = '" + userid + "'"
            con.cursor.execute(updatequery)
            con.db.commit()

        elif membercount == 1:
            query = "update ROOMINFO set user2 = '" + userid + "', membercount = 2 where roomindex = '" + roomindex + "'"
            result = {"result": 1, "roomindex": roomindex}

            updatequery = "update USERINFO set lastroom = '" + roomindex + "' where userid = '" + userid + "'"
            con.cursor.execute(updatequery)
            con.db.commit()

        elif membercount == 2:
            query = "update ROOMINFO set user3 = '" + userid + "', membercount = 3 where roomindex = '" + roomindex + "'"
            result = {"result": 1, "roomindex": roomindex}

            updatequery = "update USERINFO set lastroom = '" + roomindex + "' where userid = '" + userid + "'"
            con.cursor.execute(updatequery)
            con.db.commit()

        elif membercount == 3:
            query = "update ROOMINFO set user4 = '" + userid + "', membercount = 4 where roomindex = '" + roomindex + "'"
            result = {"result": 1, "roomindex": roomindex}

            updatequery = "update USERINFO set lastroom = '" + roomindex + "' where userid = '" + userid + "'"
            con.cursor.execute(updatequery)
            con.db.commit()

        else:
            result = {"result": 2, "roomindex": "full"}

    else:
        result = {"result": -1, "roomindex": "something wrong"}


    con.cursor.execute(query)
    con.db.commit()
    con.close()

    return jsonify(result)



@app.route("/leave_room")
def leave_room():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    userid = request.args.get("userid")

    checkquery = "select user1, user2, user3, user4, membercount from ROOMINFO where roomindex= '" + roomindex + "'"
    con.cursor.execute(checkquery)
    query_result = con.cursor.fetchall()

    membercount = query_result[0][4]

    if query_result[0][0] == userid:
        mynumber = 1
    elif query_result[0][1] == userid:
        mynumber = 2
    elif query_result[0][2] == userid:
        mynumber = 3
    else:
        mynumber = 4

    updatequery = "update ROOMINFO set user" + str(mynumber) + "= null, membercount = '" + str(membercount - 1) + "' where roomindex = '" + roomindex + "'"
    con.cursor.execute(updatequery)
    con.db.commit()

    updatequery1 = "update USERINFO set lastroom = NULL where userid = '" + userid + "'"
    con.cursor.execute(updatequery1)
    con.db.commit()


    con.close()
    return jsonify({'result': 1})



@app.route("/save_calendar")
def save_calendar():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    color = request.args.get("color")
    title = request.args.get("title")
    startdate = request.args.get("startdate")
    enddate = request.args.get("enddate")
    remind = request.args.get("remind")
    memo = request.args.get("memo")

    query = """insert into CALENDAR (roomindex, color, title, startdate, enddate, remind, memo) 
                    values (%s, %s, %s, %s, %s, %s, %s)"""
    con.cursor.execute(query, (roomindex, color, title, startdate, enddate, remind, memo))
    con.db.commit()
    con.close()

    return jsonify({'result': 1})


@app.route("/get_calendar")
def get_calendar():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    date = request.args.get("date")

    query = "select color, title, startdate, enddate, remind, calendarindex from CALENDAR where roomindex = '" + roomindex + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    calinfo = []

    if (query_result):
        for row in query_result:
            if row[2][:7] == date or row[3][:7] == date:
                calinfo.append(
                    {"color": row[0], "title": row[1], "startdate": row[2], "enddate": row[3], "remind": row[4], "calendarindex": row[5]})
        result = {'result': 1, 'calinfo': calinfo}
    else:
        result = {'result': -1}

    con.close()
    return jsonify(result)


# TODO
@app.route("/get_birthday")
def get_birthday():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    month = request.args.get("month")

    query = "select user1, user2, user3, user4 from ROOMINFO where roomindex = '" + roomindex + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    birthdayinfo = []

    for i in range(4):
        if query_result[0][i] != "null":
            query1 = "select name, birthday from USERINFO where userid = '" + str(query_result[0][i]) + "'"
            con.cursor.execute(query1)
            query_result1 = con.cursor.fetchall()

            # birthday = query_result1[0][1]
            # if birthday[5:7] == month:
                # birthdayinfo.append({"name": query_result1[0][0], "birthday": query_result1[0][1]})


    return jsonify({"result": 1, "birthdayinfo": query_result[0][0]})



@app.route("/delete_calendar")
def delete_calendar():
    con = Mysql()
    calindex = request.args.get("calindex")

    query = "delete from CALENDAR where calendarindex = '" + calindex + "'"

    con.cursor.execute(query)
    con.db.commit()
    con.close()

    return jsonify({'result': 1})


#  게시판 저장
@app.route("/put_board")
def put_board():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    content = request.args.get("content")
    userid = request.args.get("userid")
    postdate = request.args.get("postdate")
    fixed = request.args.get("fixed")

    query = """insert into BOARD (roomindex, content, userid, postdate, fixed) 
                    values (%s, %s, %s, %s, %s)"""
    con.cursor.execute(query, (roomindex, content, userid, postdate, fixed))
    con.db.commit()
    con.close()
    return jsonify({'result': 1})


#  할일 저장
@app.route("/put_todo")
def put_todo():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    title = request.args.get("title")
    duedate = request.args.get("duedate")
    score = request.args.get("score")
    remind = request.args.get("remind")

    query = """insert into TODO (roomindex, title, duedate, score, remind) 
                    values (%s, %s, %s, %s, %s)"""
    con.cursor.execute(query, (roomindex, title, duedate, score, remind))
    con.db.commit()
    con.close()
    return jsonify({'result': 1})


#  할일 불러오기
@app.route("/get_todo")
def get_todo():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    duedate = request.args.get("duedate")
    filter = request.args.get("filter")

    if filter == "0":
        query = "select todoindex, title, duedate, score, remind, userid, donedate from TODO where roomindex = '" + roomindex + "'"
        con.cursor.execute(query)
        query_result = con.cursor.fetchall()
    elif filter == "1":
        query = "select todoindex, title, duedate, score, remind, userid, donedate from TODO where roomindex = '" + roomindex + "' and duedate = '" + duedate + "'"
        con.cursor.execute(query)
        query_result = con.cursor.fetchall()
    else: # todo
        query = "select todoindex, title, duedate, score, remind, userid, donedate from TODO where roomindex = '" + roomindex + "' and duedate = '" + duedate + "'"
        con.cursor.execute(query)
        query_result = con.cursor.fetchall()


    todo = []

    for row in query_result:
        todo.append({"index": row[0], 'title': row[1], 'duedate': row[2], 'score': row[3], 'remind': row[4], 'userid': row[5], 'donedate': row[6]})

    if (query_result):
        result = {'result': 1, 'todo': todo}
    else:
        result = {'result': -1}

    con.close()
    return jsonify(result)



@app.route("/get_calendar_home")
def get_calendar_home():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    date = request.args.get("date")

    query = "select color, title, startdate from CALENDAR where roomindex = '" + roomindex + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    calinfo = []

    # if (query_result):
    #     for row in query_result:
    #         if row[2][:7] == date or row[3][:7] == date:
    #             calinfo.append(
    #                 {"color": row[0], "title": row[1], "startdate": row[2], "enddate": row[3], "remind": row[4], "calendarindex": row[5]})
    #     result = {'result': 1, 'calinfo': calinfo}
    # else:
    #     result = {'result': -1}

    con.close()
    return jsonify(query_result)




@app.route("/update_todo")
def update_todo():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    userid = request.args.get("userid")
    donedate = request.args.get("donedate")
    todoindex = request.args.get("todoindex")
    year = request.args.get("year")
    month = request.args.get("month")
    weekofMonth = request.args.get("weekofMonth")

    query = "update TODO set userid = '" + userid + "', donedate = '" + donedate + "' where todoindex = '" + todoindex + "'"
    con.cursor.execute(query)
    con.db.commit()

    scorequery = "select score from TODO where todoindex = '" + todoindex + "'"
    con.cursor.execute(scorequery)
    score_result = con.cursor.fetchall()


    selectquery = "select content, score from STATS where roomindex = '" + roomindex + "' and weekofMonth = '" + weekofMonth + "' and year = '" + year + "' and month = '" + month + "' and userid = '" + userid + "'"
    con.cursor.execute(selectquery)
    select_result = con.cursor.fetchall()

    if (select_result == ()):
        query = """insert into STATS (roomindex, userid, year, month, weekofMonth, score) 
                            values (%s, %s, %s, %s, %s, %s)"""
        con.cursor.execute(query, (roomindex, userid, year, month, weekofMonth, 0))
        con.db.commit()

        contentstr = todoindex
        score = int(score_result[0][0])

    else:
        contentstr = str(select_result[0][0]) + "," + todoindex
        score = int(select_result[0][1]) + int(score_result[0][0])


    query1 = "update STATS set content = '" + contentstr + "', score = '" + str(score) + "' where roomindex = '" + roomindex + "' and weekofMonth = '" + weekofMonth + "' and year = '" + year + "' and month = '" + month + "' and userid = '" + userid + "'"
    con.cursor.execute(query1)
    con.db.commit()

    con.close()
    return jsonify({'result': 1})


@app.route("/undo_todo")
def undo_todo():
    con = Mysql()

    todoindex = request.args.get("todoindex")

    query = "update TODO set userid = 'null', donedate = 'null' where todoindex = '" + todoindex + "'"
    con.cursor.execute(query)
    con.db.commit()




    con.close()
    return jsonify({'result': 1})



@app.route("/get_board")
def get_board():
    con = Mysql()
    roomindex = request.args.get("roomindex")

    query = "select boardindex, content, userid, postdate, fixed from BOARD where roomindex = '" + roomindex + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    boardinfo = []


    for row in query_result:

        boardinfo.append({"boardindex": row[0], "content": row[1], "userid": row[2], "postdate": row[3], "fixed": row[4]})

    result = {'result': 1, 'boardinfo': boardinfo}

    con.close()
    return jsonify(result)


@app.route("/delete_board")
def delete_board():
    con = Mysql()
    boardindex = request.args.get("boardindex")

    query = "delete from BOARD where boardindex = '" + boardindex + "'"

    con.cursor.execute(query)
    con.db.commit()
    con.close()

    return jsonify({'result': 1})

@app.route("/update_board")
def update_board():
    con = Mysql()
    content = request.args.get("content")
    fixed = request.args.get("fixed")
    boardindex = request.args.get("boardindex")

    query = "update BOARD set content = '" + content + "', fixed = '" + fixed + "' where boardindex = '" + boardindex + "'"
    con.cursor.execute(query)
    con.db.commit()
    con.close()
    return jsonify({'result': 1})


@app.route("/get_stats")
def get_stats():
    con = Mysql()
    roomindex = request.args.get("roomindex")
    year = request.args.get("year")
    month = request.args.get("month")

    query = "select userid, weekofMonth, content, score from STATS where roomindex = '" + roomindex + "' and year = '" + year + "' and month = '" + month + "'"
    con.cursor.execute(query)
    query_result = con.cursor.fetchall()

    statsinfo = []

    for row in query_result:
        contentinfo = []

        arr = row[2].split(',')
        for each in arr:
            query1 = "select title, score, donedate from TODO where todoindex = '" + each + "'"
            con.cursor.execute(query1)
            query_result1 = con.cursor.fetchall()
            contentinfo.append(query_result1)

        statsinfo.append({"userid": row[0], "weekofMonth": row[1], "content": contentinfo, "score": row[3]})

    result = {'result': 1, 'statsinfo': statsinfo}

    con.close()
    return jsonify(result)


# #  초대 코드 업데이트
# @app.route("/update_randomcode")
# def update_randomcode():
#     con = Mysql()
#     roomindex = request.args.get("roomindex")
#
#     updatequery = "update INVITESTRING set randomstring = left(MD5('" + roomindex + "'),9) where roomindex = '" + roomindex + "'"
#     con.cursor.execute(updatequery)
#     con.db.commit()
#
#     query = "select randomstring from INVITESTRING where roomindex= '" + roomindex + "'"
#     con.cursor.execute(query)
#     finalresult = con.cursor.fetchall()
#
#     if (finalresult):
#         result = {'result': 1, 'roominfo': finalresult[0][0]}
#     else:
#         result = {'result': -1}
#
#     con.close()
#     return jsonify(result)
#
#



app.run(host='0.0.0.0', port = 5000)
