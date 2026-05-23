<html>
    <head>  
        <title> @yield('titulo')</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>

        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body>
        <div class=>

    </body>
        <nav>
            <div class="nav-wrapper deep-orange">
            <a href="#!" class="brand-logo">Projeto cursos</a>
            <a href="#" data-target="mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
            <ul class="right hide-on-med-and-down">
                <li><a href="/">Home</a></li>
                <li><a href="/login">Login</a></li>
                <li><a href="{{route('admin.alunos')}}">Alunos</a></li>
                <li><a href="{{route('admin.cursos')}}">Cursos</a></li>
            </ul>
            </div>
        </nav>

        <ul class="sidenav" id="mobile">
             <li><a href="/">Home</a></li>
            <li><a href="/login">Login</a></li>
        </ul>
</html>