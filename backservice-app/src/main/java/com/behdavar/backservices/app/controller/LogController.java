package com.behdavar.backservices.app.controller;

import com.behdavar.backservices.app.model.FileModel;
import com.behdavar.backservices.common.util.validation.NullValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/v1/logs")
public class LogController {
    private static final Logger LOGGER = LogManager.getLogger(LogController.class);

    private static Map<Integer, FileModel> map = new HashMap<>();
    private static final int rootKey = Paths.get("c:/logs").hashCode();

    @PostConstruct
    public void init() {
        map.clear();
        Path path = Paths.get("c:/logs");
        FileModel root = FileModel.ofDirectory(rootKey, null, path);
        map.put(rootKey, root);
    }

    @GetMapping("/folder/{folder}")
    public ResponseEntity<List<FileModel>> fileList(@PathVariable("folder") Integer requestPath) {
        EntryMessage traceEntry = LOGGER.traceEntry("folder: {}", requestPath);

        FileModel folder;
        if (Objects.isNull(requestPath) || Objects.equals(requestPath, -1)) {
            folder = map.get(rootKey);
        } else {
            folder = map.get(requestPath);
        }

        if (Objects.isNull(folder) || !folder.isDirectory()) {
            throw new IllegalArgumentException("folder is invalid");
        }

        folder.getChildren().clear();
        try {
            Files.list(folder.getPath()).forEach(p -> {
                FileModel add = null;
                if (Files.isDirectory(p)) {
                    add = FileModel.ofDirectory(p.hashCode(), folder, p);
                }

                if (Files.isRegularFile(p)) {
                    add = FileModel.ofFile(p.hashCode(), folder, p);
                }

                if (Objects.nonNull(add)) {
                    folder.getChildren().add(add);
                    map.put(add.getId(), add);
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("folder is invalid");
        }

        return LOGGER.traceExit(traceEntry, ResponseEntity.ok(folder.getChildren()));
    }

    @GetMapping("/file/{file}/{from}/{to}")
    public ResponseEntity<List<String>> browseFile(@PathVariable("file") Integer fileId,
                                                   @PathVariable("from") Integer from,
                                                   @PathVariable("to") Integer to) throws IOException {
        EntryMessage traceEntry = LOGGER.traceEntry("file: {}, from: {}, to: {}", fileId, from, to);

        NullValidator.isNullThrow(() -> new IllegalArgumentException("input params is invalid"), fileId, from, to);
        FileModel fileModel = map.get(fileId);
        NullValidator.isNullThrow(() -> new IllegalArgumentException("fileModel is invalid"), fileModel, fileModel.getPath());
        if (from > to) {
            throw new IllegalArgumentException("from is greater then to");
        }
        if (fileModel.isDirectory()) {
            throw new IllegalArgumentException("requested is directory");
        }
        Path path = fileModel.getPath();
        List<String> result = Files.lines(path)
                .skip(from)
                .limit(to - from)
                .collect(Collectors.toList());

        return LOGGER.traceExit(traceEntry, ResponseEntity.ok(result));
    }
}
