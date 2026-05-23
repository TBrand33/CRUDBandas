<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Aluno extends Model
{
    protected $fillable = [
        'nome', 'celular', 'imagem', 'id_curso'
    ];

    public function curso()
    {
        return $this->belongsTo(Curso::class, 'id_curso');
    }
}
