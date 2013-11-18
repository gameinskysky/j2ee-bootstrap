<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
       value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>

<!DOCTYPE html>
<html>
<head>
    <title>J2EE Bootstrap</title>
    <link rel="stylesheet" href="${baseURL}/static/css/reset.css">
    <link rel="stylesheet" href="${baseURL}/static/css/style.css">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>

<div class="right">
    <p>
    Logged in as ${it.user.login}
    </p>
    <p>
        <a href="${baseURL}/logout">logout</a>
    </p>
</div>

<h1>Simple TODO-list app</h1>

<input id="newTaskInput" type="text" placeholder="Enter new task here"/>

<ul id="tasksList">
</ul>

<script>

    $(function () {
        $("#newTaskInput").keyup(function (event) {
            if (event.keyCode == 13) {
                submitTask($("#newTaskInput").val());
            }
        });
        loadTasks();
    });

    function appendTask(task) {
        var li = $('<li></li>');
        var leftDiv = $('<div></div>').addClass('left');

        for (var i = 0; i < task.tags.length; i++) {
            var tag = $('<span></span>').addClass('tag');
            tag.append(task.tags[i]);
            //TODO: set class with color here
            leftDiv.append(tag);
        }
        var taskBody = leftDiv.append(task.task);
        var taskControls = $('<div></div>').addClass('right').addClass('delete');
        taskControls.append('<a href=\"javascript:void(0);\" data-ref=\"' + task.id + '\">Delete</a>');
        li.append($('<div></div>').addClass('clearfix').append(taskBody).append(taskControls));
        $("#tasksList").append(li);
    }

    function loadTasks() {
        $("#tasksList").empty();
        $.get("api/tasks/", function(response) {
            response.map(appendTask);
            updateListeners();
            console.log(response);
        });
    }

    function submitTask(task) {
        console.log('Submitting: ' + task);
        $.post("api/tasks/add", {task: task}, function (response) {
                    appendTask(response);
                    updateListeners();
                    $('#newTaskInput').val(response.tags.join(' '));
                }
        );
    }

    function updateListeners() {
        $("li").find("div[class='right delete']").hide();
        $("li").unbind().mouseover(function(e) {
                    $(this).find("div[class='right delete']").show();
                })
                .mouseout(function(e) {
                    $(this).find("div[class='right delete']").hide();
                });
        $("li").find("a").unbind().click(function (e) {
            var taskId = $(this).attr("data-ref");
            console.log('deleting task: ' + taskId);
            $.post("api/tasks/delete", {id: taskId}, function(response) {
                loadTasks();
            });
        });
    }

</script>
</body>
</html>
