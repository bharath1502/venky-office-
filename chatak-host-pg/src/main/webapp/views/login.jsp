<html>
<head>
<link href="../css/pg.css" rel="stylesheet">
</head>
<body>
<div class="login">
	<h1>Authentication >></h1>
	<h5 style="color:red;">${error }</h5>
    <form method="post" action="chatakUserAuthenticate">
    	<input type="text" name="uPG" placeholder="Username" required="required" autocomplete="off" />
        <input type="password" name="pPG" placeholder="Password" required="required" autocomplete="off" />
        <button type="submit" class="btn btn-primary btn-block btn-large">Start Chatak Payment Gateway Acquirer</button>
    </form>
</div>
</body>
</html>
