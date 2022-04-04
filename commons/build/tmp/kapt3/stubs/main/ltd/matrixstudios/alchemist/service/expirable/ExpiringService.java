package ltd.matrixstudios.alchemist.service.expirable;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H&J\u0015\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lltd/matrixstudios/alchemist/service/expirable/ExpiringService;", "T", "", "()V", "clearOutModels", "", "getValues", "", "save", "element", "(Ljava/lang/Object;)V", "commons"})
public abstract class ExpiringService<T extends java.lang.Object> {
    
    public ExpiringService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<T> getValues();
    
    public abstract void save(T element);
    
    public abstract void clearOutModels();
}