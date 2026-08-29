<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class UsuarioSeeder extends Seeder
{
    public function run(): void
    {
        $emails = [
            'admin@gmail',
            'admin@email',
            'admin@gmail.com',
            'admin@email.com'
        ];

        foreach ($emails as $email) {
            User::updateOrCreate(
                ['email' => $email],
                [
                    'name' => 'Tiago',
                    'password' => bcrypt('123')
                ]
            );
        }
    }
}

