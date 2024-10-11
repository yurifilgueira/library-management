package com.library.controllers.util;

import com.library.entities.dto.requests.RegisterRequestDto;
import com.library.entities.dto.requests.UpdateUserRequestDto;

public class DataVerifier {

    public static boolean dataIsNull(RegisterRequestDto data) {

        if (data == null) return true;
        else if (data.name() == null || data.name().isEmpty()) return true;
        else if (data.email() == null || data.email().isEmpty()) return true;
        else if (data.password() == null || data.password().isEmpty()) return true;

        return false;
    }

    public static boolean dataIsNull(UpdateUserRequestDto data) {

        if (data == null) return true;
        else if (data.id() == null) return true;
        else if (data.name() == null || data.name().isEmpty()) return true;
        else if (data.email() == null || data.email().isEmpty()) return true;
        else if (data.newPassword() == null || data.newPassword().isEmpty()) return true;
        else if (data.confirmNewPassword() == null || data.confirmNewPassword().isEmpty()) return true;

        return false;
    }

}
