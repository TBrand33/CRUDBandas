@extends('layout.site')
@section('titulo', 'Login')

@section('conteudo')
<div class="container">
    <h3 class="center">Entrar</h3>
    
    @if(session('error'))
        <div class="card-panel red lighten-4 red-text text-darken-4">
            {{ session('error') }}
        </div>
    @endif

    <div class="row">
        <form action="{{ route('login.entrar') }}" method="post">
            {{ csrf_field() }}
            
            <div class="input-field">
                <input type="text" name="email" id="email" required>
                <label for="email">E-mail</label>
            </div>
            
            <div class="input-field">
                <input type="password" name="senha" id="senha" required>
                <label for="senha">Senha</label>
            </div>
            
            <button type="submit" class="btn deep-orange waves-effect waves-light">Entrar</button>
            <a href="{{ route('auth.google') }}" class="btn blue darken-2 waves-effect waves-light" style="margin-left: 10px;">
                <i class="material-icons left">account_circle</i> Entrar com o Google
            </a>
        </form>
    </div>
</div>
@endsection