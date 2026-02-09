<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<form method="post">
    <label title="Email">
        <input name="username" type="text" value="${sessionScope.credentials.username}">
    </label><br/>
    <label title="Password">
        <input name="password" type="password" value="${sessionScope.credentials.password}">
    </label><br/>
    <button type="submit">Log in</button>
</form>
</body>
</html>