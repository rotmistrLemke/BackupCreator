import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class BackupCreator {
    public static void createBackup(String sourceDir) throws IOException {
        Path backupDir = Paths.get(sourceDir, "backup");
        if (!Files.exists(backupDir)) {
            Files.createDirectory(backupDir);
        }

        Files.walkFileTree(Paths.get(sourceDir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.getParent().equals(backupDir)) {
                    Path target = backupDir.resolve(file.getFileName());
                    Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Backup created successfully in " + backupDir);
    }

    public static void main(String[] args) {
        try {
            createBackup(".");
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
}