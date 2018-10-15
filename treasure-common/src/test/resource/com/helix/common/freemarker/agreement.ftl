<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
            <title>
            </title>
            <style>
            </style>
        </meta>
    </head>
    <body style="font-family: SimSun">
        <div style="text-align: center;">
            <span style="font-family:SimSun;">
               品牌：${(brand)!"&nbsp;&nbsp;"}
            </span>
            <span style="font-family:SimSun;">
                ${(model.openDate?date("yyyy-MM-dd")?string("yyyy"))!"&nbsp;&nbsp;&nbsp;&nbsp;"}年
            </span>
        </div>
    </body>
</html>