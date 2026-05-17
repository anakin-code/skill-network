package simplex.bn26.master.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import simplex.bn26.master.server.SkillException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // バリデーションエラー
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    // Skillエラー
    @ExceptionHandler(SkillException.class)
    public ResponseEntity<Map<String, String>> handleSkillException(SkillException ex) {
        Map<String, String> error = new HashMap<>();

        if (ex.getMessage() != null) {
            error.put("message", ex.getMessage());
        }

        return ResponseEntity.badRequest().body(error);
    }

    // 想定外エラー
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> error = new HashMap<>();

        error.put("exception", ex.getClass().getSimpleName());
        error.put("message", ex.getMessage());

        return ResponseEntity.internalServerError().body(error);
    }
}