package com.example.security.constant;

public class Constant {
    public static final String AES = "AES";
    public static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final int SIX_TEEN = 16;
    public static final int ZERO = 0;
    public static final String ASTERISK = "*";
    public static final String SLASH = "/";
    public static final String SLASH_ASTERISK_ASTERISK = "/**";
    public static final String SUCCESS = "Success";
    public static final String ERROR = "ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String HTTP_METHOD_NOT_ALLOWED = "HTTP_METHOD_NOT_ALLOWED";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String UNKNOWN = "unknown";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";

    // Exception messages
    public static final String RUNTIME_EXCEPTION_OCCURRED = "RuntimeException occurred: {}";
    public static final String IO_EXCEPTION_OCCURRED = "IOException occurred: {}";
    public static final String NOT_FOUND_EXCEPTION_OCCURRED = "NotFoundException occurred: {}";
    public static final String BAD_REQUEST_EXCEPTION_OCCURRED = "BadRequestException occurred: {}";
    public static final String VALIDATION_EXCEPTION_OCCURRED = "Validation exception occurred: {}";
    public static final String UNEXPECTED_EXCEPTION_OCCURRED = "Unexpected exception occurred: {}";

    // IllegalArgumentException messages
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_OCCURRED = "IllegalArgumentException occurred: {}";

    // MethodArgumentTypeMismatchException messages
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION_OCCURRED = "MethodArgumentTypeMismatchException occurred: {}";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH = "Parameter '%s' is of invalid type. Expected type: %s.";

    // NoResourceFoundException messages
    public static final String NO_RESOURCE_FOUND_EXCEPTION_OCCURRED = "NoResourceFoundException occurred: {}";
    // Specific error messages
    public static final String VALIDATION_FAILED = "Validation failed for one or more fields.";

    // Database-related messages
    public static final String DATABASE_EXCEPTION_OCCURRED = "Database exception occurred: {}";
    public static final String DATABASE_ERROR_OCCURRED = "A database error occurred.";

    // Database constraint violation messages
    public static final String DATA_INTEGRITY_VIOLATION_OCCURRED = "DataIntegrityViolationException occurred: {}";

    // File size limit messages
    public static final String FILE_SIZE_LIMIT_EXCEEDED = "File size exceeds the maximum allowed limit: {}";
    public static final String FILE_TOO_LARGE = "The uploaded file is too large. Please upload a file smaller than the allowed size.";

    // Conflict exception messages
    public static final String CONFLICT_EXCEPTION_OCCURRED = "Conflict exception occurred.";
    public static final String CONFLICT = "CONFLICT_ERROR.";
    public static final String HttpRequestMethodNotSupportedException = "HttpRequestMethodNotSupportedException occurred: {}";
    public static final String HTTP_METHOD_NOT_SUPPORT = "HTTP method not supported: ";
    // HttpMessageNotReadableException messages
    public static final String INVALID_REQUEST_BODY = "INVALID_REQUEST_BODY";
    public static final String FAILED_TO_READ_REQUEST_BODY = "Failed to read HTTP request body";
    // Cloudinary-related messages
    public static final String UPLOAD_SEGMENT = "/upload/";
    public static final String VERSION_PREFIX_REGEX = "v[0-9]+/";
    public static final String FILE_EXTENSION_REGEX = "\\.[^.]+$";
    public static final String EMPTY_STRING = "";
}
