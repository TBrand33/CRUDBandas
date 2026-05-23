@extends('layout.site')
@section('titulo','Aluno')
@section('conteudo')
<div class='container'>
<h3 class='center'>Lista de Alunos</h3>
    <div class='row'>
        <table>
            <thead>
                <tr> 
                    <td>Id</td>
                    <td>Nome</td>
                    <td>Celular</td>
                    <td>Curso</td>
                    <td>Imagem</td>
                </tr>

            </thead>
            <tbody>
            @foreach($rows as $row) 
            <tr>
                <td>{{ $row->id }}</td>
                <td>{{ $row->nome }}</td>
                <td>{{ $row->celular }}</td>
                <td>{{ $row->id_curso && $row->curso ? $row->curso->titulo : 'Sem curso' }}</td>
                <td><img  width = 150 src="{{ asset($row->imagem) }}" alt="{{ $row->titulo }}"></td>
                <td> 
                    <a class='btn deep-orange' href="{{ route('admin.alunos.editar',$row->id) }}">Alterar</a>
                    <a class='btn rede' href="{{ route('admin.alunos.excluir',$row->id) }}">Excluir</a>
                </td>
            </tr>
            @endforeach
            </tbody>
        </table>
    </div>
    <div class='row'> 
        <a class='btn blue' href="{{ route('admin.alunos.adicionar')}}">Adicionar</a>
    </div>
    </div>
@endsection