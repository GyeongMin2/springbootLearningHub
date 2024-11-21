const express = require('express');
const mysql = require('mysql2');

const app = express();
const port = 3000;


const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'fullstack7'
});

connection.connect((err) => {
    if (err) {
        console.error('MySQL 연결 실패:', err);
        return;
    }
    console.log('MySQL에 연결되었습니다.');
});


function userInfo(userId) {
  connection.query('SELECT id, name, member_status FROM tbl_member WHERE id = ?', [userId], (err, rows, fields) => {
      if (err) throw err;
      return rows[0];
  });
}

function setCookie(res, userInfo) {
  res.cookie('user', JSON.stringify(userInfo), {httpOnly: true, secure: true, maxAge: 1000 * 60 * 60 * 24 * 30});
}

app.listen(port, () => {
    console.log(`${port}포트 실행 중.`);
});

app.get('/login', (req, res) => {
  console.log(req.query);
  console.log(req.query.userId);
  console.log(req.query.password);
    connection.query('SELECT password FROM tbl_member WHERE id = ?', [req.query.userId], (err, rows, fields) => {
        if (err) throw err;
        let user = rows[0];
        if(user) {
            if(user.password === req.query.password) {
                let loginUserInfo = userInfo(req.query.userId);
                setCookie(res, loginUserInfo);
                res.send({result: 'success', userInfo: loginUserInfo});
            } else {
                res.send({result: 'fail'});
            }
        } else {
            res.send({result: 'fail'});
        }
    });
});
