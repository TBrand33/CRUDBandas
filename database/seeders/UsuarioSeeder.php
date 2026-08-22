<?php

namespace Database\Seeders;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class UsuarioSeeder extends Seeder
{
    public function run() { // ex insere um adm no
        $dados=[
            'name'=>"Tiago", 
            'email'=>"admin@email",
            'password'=>bcrypt("123")
        ];
        User::create($dados);
    }
}
