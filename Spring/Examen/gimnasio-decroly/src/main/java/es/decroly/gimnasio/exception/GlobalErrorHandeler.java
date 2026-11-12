package es.decroly.gimnasio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandeler {

    @ExceptionHandler(RecursosNoEncontradosExeption.class)
    public ProblemDetail recursosNoEncontradosExeption(RecursosNoEncontradosExeption e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("Recurso no encontrado");
        return problem;
    }

    @ExceptionHandler(DatosNoValidosException.class)
    public ProblemDetail datosNoValidosException(DatosNoValidosException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problem.setTitle("Peticion no valida");
        return problem;
    }

   @ExceptionHandler(ConflictosException.class)
    public ProblemDetail conflictosException(ConflictosException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problem.setTitle("Conflicto con el recurso");
        return problem;
    }


}
