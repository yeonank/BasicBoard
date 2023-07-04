var main = {
    init : function (){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
//jquery 사용중 $(선택자).동작함수()
        $('#btn-update').on('click', function(){
            _this.update();
        });

        $('#btn-delete').on('click', function(){
            _this.delete();
        });
    },
    save: function(){
        var data = {
            title: $('#title').val(),//val()은 form의 값을 가져오는 역할 id가 title인 input 요소 값 가져감
            author: $('#author').val(),
            content: $('#content').val(),
        };
        $.ajax({//ajax는 비동기 방식!
            type : 'POST',
            url: '/api/board',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';//리다이렉션
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    update:function(){
        var data={
            title: $('#title').val(),
            content: $('#content').val(),
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            datatype: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    delete : function (){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType:'json',
            contentType:'application/json; charset=utf-8'
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href="/"
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};

main.init();//중복 함수 이름으로 인한 문제를 피하기 위해 index.js만의 유효범위를 만들음