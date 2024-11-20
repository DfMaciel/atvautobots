package com.autobots.automanager.controllers;

import java.util.Optional;

public record CredencialDto(
    Optional<String> nomeUsuario,
    Optional<String> senha,
    Optional<Long> codigo
) {
}
