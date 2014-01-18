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

    <script src="${baseURL}/static/js/jquery-1.10.2.min.js"></script>
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
<h2>Today</h2>
<ul id="tasksList">
</ul>

<h2>Done</h2>
<ul id="doneList">
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

    function appendTask(task, target, command) {
        var li = $('<li></li>');
        var body = $('<div></div>').addClass('clearfix');

        for (var i = 0; i < task.tags.length; i++) {
            var tag = $('<div></div>').addClass('left').addClass('tag');
            tag.append(task.tags[i]);
            //TODO: set class with color here
            body.append(tag);
        }
        body.append($('<div></div>').addClass('left').addClass('text').append(task.task));
        var taskControls = $('<div></div>').addClass('right').addClass('command');
        taskControls.append('<a href=\"javascript:void(0);\" data-ref=\"' + task.id + '\">' + command + '</a>');
        body.append(taskControls);
        li.append(body);
        $(target).append(li);
    }

    function loadTasks() {
        $("#tasksList").empty();
        $("#doneList").empty();
        $.get("api/tasks/", function(response) {
            console.log(response);
            response.active.map(function(task) {
                appendTask(task, '#tasksList', 'complete');
            });
            response.done.map(function(task) {
                appendTask(task, '#doneList', 'delete');
            });

            updateListeners();

        });
    }

    function submitTask(task) {
        console.log('Submitting: ' + task);
        $.post("api/tasks/add", {task: task}, function (response) {
                    appendTask(response, '#tasksList', 'complete');
                    updateListeners();
                    $('#newTaskInput').val(response.tags.join(' '));
                }
        );
    }

    function updateListeners() {
        $("li").find("div[class='right command']").hide();
        $("li").unbind().mouseover(function(e) {
                    $(this).find("div[class='right command']").show();
                })
                .mouseout(function(e) {
                    $(this).find("div[class='right command']").hide();
                });
        $("li").find("a").unbind().click(function (e) {
            var taskId = $(this).attr("data-ref");
            var url = 'api/tasks/' + $(this).text();
            console.log('sending command: ' +url + '; taskId: ' + taskId);
            $.post(url, {id: taskId}, function(response) {
                loadTasks();
            });
        });
    }

</script>
</body>
</html>
