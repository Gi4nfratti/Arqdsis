<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg">
	<div class="container">

		<a class="navbar-brand" href="inicio">Hora da Pipoca</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item active"><a class="nav-link"
					href="listar_filmes">Filmes</a></li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Catálogo</a>
	                <div class="dropdown-menu dropdown-primary" aria-labelledby="navbarDropdownMenuLink">
	                    <a class="dropdown-item" href="ordenar_genero">Por Gênero</a>
	                    <a class="dropdown-item" href="ordenar_popularidade">Por Popularidade</a>
	                    <a class="dropdown-item" href="ordenar_data">Por Lançamento</a>
	                </div>
				</li>
			</ul>
		</div>
	</div>
</nav>
