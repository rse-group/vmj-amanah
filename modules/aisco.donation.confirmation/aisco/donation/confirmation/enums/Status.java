package aisco.donation.confirmation;

public enum Status {
    PENDING("PENDING", 0),
    BERHASIL("BERHASIL", 1),
    DITOLAK("DITOLAK", 2);

    private final String statusName;
    private final int statusId;

    private Status(String name, int id) {
        statusName = name;
        statusId = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStatusId() {
        return statusId;
    }

    public static Status findStatusById(int id) {
        for (Status status : Status.values()) {
            if (status.getStatusId() == id)
                return status;
        }
        return null;
    }

    public static Status findStatusByName(String name) {
        for (Status status : Status.values()) {
            if (status.getStatusName().equals(name))
                return status;
        }
        return null;
    }
}