var main ={
  init : function(){
    var _this = this;
    $('#btn-join').on('click', function(){
      _this.join();
    });
  },
  join:function(){
    var data = {
      userid: $('#user-id').val(),
      password: $('#password').val(),
      email: $('#email').val()
    };

    $.ajax({
      type:'POST',
      url:'/api/v1/member',
      dataType:'json',
      contentType:'application/json; charset=utf8',
      data:JSON.stringify(data)
    }).done(function() {
      alert('회워가입되었습니다.');
      window.location.href='/';
    }).fail(function(error){
      alert(JSON.stringify(error));
    });
  }
};