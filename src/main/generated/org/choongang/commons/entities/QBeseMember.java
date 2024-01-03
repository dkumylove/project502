package org.choongang.commons.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBeseMember is a Querydsl query type for BeseMember
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBeseMember extends EntityPathBase<BeseMember> {

    private static final long serialVersionUID = -1209765030L;

    public static final QBeseMember beseMember = new QBeseMember("beseMember");

    public final QBase _super = new QBase(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath modifiedBy = createString("modifiedBy");

    public QBeseMember(String variable) {
        super(BeseMember.class, forVariable(variable));
    }

    public QBeseMember(Path<? extends BeseMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBeseMember(PathMetadata metadata) {
        super(BeseMember.class, metadata);
    }

}

