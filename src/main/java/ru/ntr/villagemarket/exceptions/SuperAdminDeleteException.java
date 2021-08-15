package ru.ntr.villagemarket.exceptions;

public class SuperAdminDeleteException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Cannot delete user with role SUPERADMIN. Operation denied";
    }
}
