<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>HOMEPAGE JDBC_DEVELOPER</title>
    <link rel="stylesheet" href="style/style.css"  id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script>$(document).ready(function(){

      $('.input').focus(function(){
        $(this).parent().find(".label-txt").addClass('label-active');
      });

      $(".input").focusout(function(){
        if ($(this).val() == '') {
          $(this).parent().find(".label-txt").removeClass('label-active');
        };
      });

    });</script>
  </head>
  <body>
  <form>
    <label>
      <p class="label-txt">ENTER YOUR EMAIL</p>
      <input name="email" type="text" class="input">
      <div class="line-box">
        <div class="line"></div>
      </div>
    </label>
    <label>
      <p class="label-txt">ENTER YOUR NAME</p>
      <input name="name" type="text" class="input">
      <div class="line-box">
        <div class="line"></div>
      </div>
    </label>
    <label>
      <p class="label-txt">ENTER YOUR PASSWORD</p>
      <input name="password" type="text" class="input">
      <div class="line-box">
        <div class="line"></div>
      </div>
    </label>
    <button type="submit">submit</button>
  </form>
  </body>
</html>
