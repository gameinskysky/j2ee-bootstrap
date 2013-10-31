<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="static/css/reset.css">
    <link rel="stylesheet" href="static/css/style.css">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
<h2>Simple TODO-list app</h2>

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

    function loadTasks() {
        $.get("api/tasks/", function(response) {
            response.map(function(task) {
                $("#tasksList").append("<li>" + task + "</li>");
            });
        });
    }

    function submitTask(task) {
        console.log('Submitting: ' + task);
        $.post("api/tasks/add", {task: task}, function (response) {
                    $("#tasksList").append("<li>" + task + "</li>");
                }
        );

    }
</script>
</body>
</html>
