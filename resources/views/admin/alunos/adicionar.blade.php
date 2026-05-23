@extends('layout.site')
@section('titulo','Aluno')
@section('conteudo')
    <div class = 'container'>
        <h3 class='center'>Adicionar Aluno</h3>
        <div>
            <form action="{{ route('admin.alunos.salvar') }}" method="POST" enctype="multipart/form-data">
                {{csrf_field()}}
                @include('admin.alunos._form')
                <button class="btn deep-orange">Salvar</button>   
            </form>
        </div>  
    </div>
@endsection