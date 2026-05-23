<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use App\Models\Aluno;

class Curso extends Model
{
    protected $table = 'cursos';

    protected $fillable = [
        'titulo',
        'descricao',
        'imagem',
        'valor',
        'publicado' 
    ];

    public function alunos()
    {
        return $this->hasMany(Aluno::class, 'id_curso');
    }
    
}


