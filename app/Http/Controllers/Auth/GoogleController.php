<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Laravel\Socialite\Facades\Socialite;
use App\Models\User;
use Illuminate\Support\Facades\Auth;
use Exception;

class GoogleController extends Controller{
    // Redireciona para o formulário do Google
    public function redirectToGoogle(){
        return Socialite::driver('google')->redirect();
    }
    // Processa o retorno do Google
    
    public function handleGoogleCallback(){
        try {
            $googleUser = Socialite::driver('google')->user();
            // Procura se já existe um usuário com este e-mail
            $user = User::where('email',

            $googleUser->getEmail())->first();

            if (!$user) {
                // Se não existir, cria um novo usuário no banco
                $user = User::create([
                'name' => $googleUser->getName(),
                'email' => $googleUser->getEmail(),
                'password' => bcrypt(\Illuminate\Support\Str::random(16)),
                // Senha aleatória fictícia
                ]);
            }
            // Realiza o login da sessão do usuário
            Auth::login($user);
            // Redireciona para a sua Dashboard ou Home
            return redirect()->intended('/admin/cursos');
        } catch (Exception $e) {
            return redirect('/login')->with('error',
                'Falha ao autenticar com o Google.');
        }
    }
}
