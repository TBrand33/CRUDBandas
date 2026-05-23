<?php

namespace App\Http\Controllers\admin;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Aluno;
use App\Models\Curso;

class AlunoController extends Controller
{
    public function index(){
        $rows = Aluno::all();

        return view('admin.alunos.index', compact('rows'),[
            'cursos' => Curso::all()
        ]);
    }

    public function adicionar(){
        return view('admin.alunos.adicionar',[
            'cursos' => Curso::all()
        ]);
    }

    public function salvar(Request $request){
       $dados = $request->all();
       if (isset($dados['publicado'])) {
            $dados['publicado'] = 'sim';
        } 
        else {
            $dados['publicado'] = 'não'; 
        }
    
        if($request->hasFile('imagem')){
            $imagem = $request->file('imagem');
            $num = rand(1111,9999);
            $dir = "img/alunos/";
            $ex = $imagem->guessClientExtension();
            $nomeImagem = "imagem_".$num.".".$ex;
            $imagem->move($dir,$nomeImagem);
            $dados['imagem'] = $dir.$nomeImagem;
        }
        Aluno::create($dados);
        return redirect()->route('admin.alunos');
    }

    public function editar($id){
        $row = Aluno::find($id);
        return view('admin.alunos.editar', compact('row'),[
            'cursos' => Curso::all()
        ]);
    }

    public function atualizar(Request $request, $id){
        $dados = $request->all();

        if (isset($dados['publicado'])) {
            $dados['publicado'] = 'sim';
        } 
        else {
            $dados['publicado'] = 'não'; 
        }

        if ($request->hasFile('imagem')) {
        $imagem = $request->file('imagem');
        $num = rand(1111, 9999); 
        $dir = "img/alunos/";
        $ext = $imagem->guessClientExtension();
        $nomeImagem = "imagem_" . $num . "." . $ext;
        $imagem->move($dir, $nomeImagem);

        $dados['imagem'] = $dir . "/" . $nomeImagem;
    }
        Aluno::find($id)->update($dados);
        return redirect()->route('admin.alunos');
    } 

    public function excluir($id){
        Aluno::find($id)->delete();
        return redirect()->route('admin.alunos');
    }
}
