<!DOCTYPE html>
<html>
<head>
    <title>Template Bootstrap 5</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="jumbotron">
                <h3 class="text-center">
                    Recuperação de senha
                </h3>
                <div class="jumbotron">
                    <h2>
                        Olá, ${user}!
                    </h2>
                    <p>Você solicitou a recuperação de senha, siga os passos a seguir para criar uma nova senha.</p>
                    <p>Utilize o código de confirmação abaixo para recuperar a sua senha no ${sistema}</p>
                    <p><strong>${token}</strong></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</html>