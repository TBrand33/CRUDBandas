    
    <div class="input-field">
        <input type="text" name="nome" value="{{ isset($linha->nome) ? $linha->nome : '' }}">
        <label>Nome</label>
    </div>
    <div class="input-field">
        <input type="text" name="celular" value="{{ isset($linha->celular) ? $linha->celular : '' }}">
        <label>Celular</label>
    </div>
    <div class="input-field">
        <input type="text" name="id_curso" value="{{ isset($linha->curso_id) ? $linha->curso_id : '' }}">
        <label>Curso ID</label>
    </div>
   <div class="file-field input-field">
    <div class="btn blue">

     <span>Imagem</span>
        <input type="file" name="imagem">
</div>
   
<div class="file-path-wrapper">
    <input class="file-path validate" type="text">
</div>

</div>
    @if( isset($row->imagem) )
        <div class="input-field">
        <img width="250" src="{{ asset($row->imagem) }}" />
        </div>
    @endif
