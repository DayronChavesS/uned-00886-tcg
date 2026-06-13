package database;

public enum SQL {
    
    /*
    ORDEN CORRECTO CREACION DE TABLAS:
    - sequence
    - catalogoregion
    - catalogooficina
    - catalogopuesto
    - catalogocaso
    - persona
    - personajuridica
    - usuario
    - inspector
    - audio
    - comparecencia
    - inspectorxcomparecencia
    - comparecenciaxaudio
    - gestionado
    - gestionante
    - representante
    - testigo
    - acompañante
    */
    
    /*
        DATA.DB NO CONTIENE NINGUN TIPO DE RESTRICCIONES
        NI CATALOGOS PARA EXPORTAR FACILMENTE LOS DATOS
    */
    CREATE_PERSONA(
            "CREATE TABLE persona (" +
            "  idPersona integer"+
            ",  cedula varchar(15)" +
            ",  primerNombre varchar(45)" +
            ",  segundoNombre varchar(45)" +
            ",  primerApellido varchar(45)" +
            ",  segundoApellido varchar(45)" +
            ")"
    ),
    CREATE_PERSONAJURIDICA(
            "CREATE TABLE personajuridica (" +
            "  idPersonaJuridica integer"+
            ",  cedulaJuridica varchar(11)" +
            ",  nombreRazonSocial varchar(45)" +
            ")"
    ),
    CREATE_USUARIO(
            "CREATE TABLE usuario (" +
            "  idPersona integer"+
            ",  email varchar(100)" +
            ",  contraseña varchar(45)" +
            ",  enLinea integer" +
            ",  autorizado VARCHAR (1)" +
            ")"
    ),
    CREATE_INSPECTOR(
            "CREATE TABLE inspector (" +
            "  idPersona integer"+
            ",  idPuesto integer" +
            ",  idRegion integer" +
            ",  idOficina integer" +
            ")"
    ),
    CREATE_AUDIO(
            "CREATE TABLE audio (" +
            "  idAudio integer"+
            ",  nombreAudio varchar(45)" +
            ",  duracionAudio double" +
            ",  pathArchivoAudio varchar(999)" +
            ",  pathArchivoAnotaciones varchar(999)" +
            ")"
    ),
    CREATE_COMPARECENCIA(
            "CREATE TABLE comparecencia (" +
            "  idComparecencia integer"+
            ",  codigoSILAC varchar(12)" +
            ",  idCaso integer" +
            ",  ubicacion varchar(200)" +
            ",  linkExpediente varchar(999)" +
            ",  fecha date" +
            ")"
    ),
    CREATE_INSPECTORXCOMPARECENCIA(
            "CREATE TABLE inspectorxcomparecencia (" +
            "  idPersona integer" +
            ",  idComparecencia integer" +
            ")"
    ),
    CREATE_AUDIOXCOMPARECENCIA(
            "CREATE TABLE audioxcomparecencia (" +
            "  idAudio integer" +
            ", idComparecencia integer" +
            ")"
    ),
    CREATE_GESTIONADO(
            "CREATE TABLE gestionado (" +
            "  idGestionado integer"+
            ",  idPersona integer" +
            ",  idPersonaJuridica integer" +
            ",  idComparecencia integer" +
            ")"
    ),
    CREATE_GESTIONANTE(
            "CREATE TABLE gestionante (" +
            "  idGestionante integer"+
            ",  idPersona integer" +
            ",  idPersonaJuridica integer" +
            ",  idComparecencia integer" +
            ")"
    ),
    CREATE_REPRESENTANTE(
            "CREATE TABLE representante (" +
            "  idPersona integer" +
            ",  idGestionante integer" +
            ",  idGestionado integer" +
            ")"
    ),
    CREATE_TESTIGO(
            "CREATE TABLE testigo (" +
            "  idPersona integer" +
            ",  idGestionante integer" +
            ",  idGestionado integer" +
            ")"
    ),
    CREATE_ACOMPAÑANTE(
            "CREATE TABLE acompañante (" +
            "  idPersona integer" +
            ",  idGestionante integer" +
            ",  idGestionado integer" +
            ",  tipoAcompañante varchar(30)" +
            ",  enCondicionDe varchar(45)" + ")"
    ),



    
    
/*******************************************************************************/    



    
    /*
        ATENCION: PARA LOS INSERTS CON IDs AUTOGENEREADOS
        SIMPLEMENTE OMITA SU VALOR EN LA CONSTRUCCION DE
        LA SENTENCIA.
    */
    
    INSERT_ACOMPAÑANTE(
        "INSERT INTO acompañante(idPersona,idGestionado,idGestionante,tipoAcompañante,enCondicionDe)VALUES(?,?,?,?,?)"
    ),
    INSERT_AUDIO(
        "INSERT INTO audio(idAudio,nombreAudio,duracionAudio,pathArchivoAudio,pathArchivoAnotaciones)VALUES(?,?,?,?,?)"
    ),
    INSERT_AUDIOXCOMPARECENCIA(
        "INSERT INTO audioxcomparecencia(idAudio,idComparecencia)VALUES(?,?)"
    ),
    INSERT_CASO(
        "INSERT INTO catalogocaso(idCaso,tipoCaso)VALUES(?,?)"
    ),
    INSERT_OFICINA(
        "INSERT INTO catalogooficina(idOficina,nombre)VALUES(?,?)"    
    ),
    INSERT_PUESTO(
        "INSERT INTO catalogopuesto(idPuesto,nombre)VALUES(?,?)"
    ),
    INSERT_REGION(
        "INSERT INTO catalogoregion(idRegion,nombre)VALUES(?,?)"
    ),
    INSERT_COMPARECENCIA(
        "INSERT INTO comparecencia(idComparecencia,codigoSILAC,idCaso,ubicacion,linkExpediente,fecha)VALUES(?,?,?,?,?,?)"
    ),
    INSERT_GESTIONADO(
        "INSERT INTO gestionado(idGestionado,idPersona,idPersonaJuridica,idComparecencia)VALUES(?,?,?,?)"
    ),
    INSERT_GESTIONANTE(
        "INSERT INTO gestionante(idGestionante,idPersona,idPersonaJuridica,idComparecencia)VALUES(?,?,?,?)"
    ),
    INSERT_INSPECTOR(
        "INSERT INTO inspector(idPersona,idPuesto,idRegion,idOficina)VALUES(?,?,?,?)"
    ),
    INSERT_INSPECTORXCOMPARECENCIA(
        "INSERT INTO inspectorxcomparecencia(idComparecencia,idPersona)VALUES(?,?)"
    ),
    INSERT_PERSONA(
        "INSERT INTO persona(idPersona,cedula,primerNombre,segundoNombre,primerApellido,segundoApellido)VALUES(?,?,?,?,?,?)"
    ),
    INSERT_PERSONAJURIDICA(
        "INSERT INTO personajuridica(idPersonaJuridica,cedulaJuridica,nombreRazonSocial)VALUES(?,?,?)"
    ),
    INSERT_REPRESENTANTE(
        "INSERT INTO representante(idPersona,idGestionante,idGestionado)VALUES(?,?,?)"
    ),
    INSERT_TESTIGO(
        "INSERT INTO testigo(idPersona,idGestionante,idGestionado)VALUES(?,?,?)"
    ),
    INSERT_USUARIO(
        "INSERT INTO usuario(idPersona,email,contraseña,enLinea)VALUES(?,?,?,?)"
    ),
    
    
    
    
   
/*******************************************************************************/    
    
    
    
    
    
    DELETE_ACOMPAÑANTE(
        "DELETE FROM acompañante"
    ),
    DELETE_AUDIO(
        "DELETE FROM audio"
    ),
    DELETE_AUDIOXCOMPARECENCIA(
        "DELETE FROM audioxcomparecencia"
    ),
    DELETE_CASO(
        "DELETE FROM catalogocaso"
    ),
    DELETE_OFICINA(
        "DELETE FROM catalogooficina"
    ),
    DELETE_PUESTO(
        "DELETE FROM catalogopuesto"
    ),
    DELETE_REGION(
        "DELETE FROM catalogoregion"
    ),
    DELETE_COMPARECENCIA(
        "DELETE FROM comparecencia"
    ),
    DELETE_GESTIONADO(
        "DELETE FROM gestionado"
    ),
    DELETE_GESTIONANTE(
        "DELETE FROM gestionante"
    ),
    DELETE_INSPECTOR(
        "DELETE FROM inspector"
    ),
    DELETE_INSPECTORXCOMPARECENCIA(
        "DELETE FROM inspectorxcomparecencia"
    ),
    DELETE_PERSONA(
        "DELETE FROM persona"
    ),
    DELETE_PERSONAJURIDICA(
        "DELETE FROM personajuridica"
    ),
    DELETE_REPRESENTANTE(
        "DELETE FROM representante"
    ),
    DELETE_TESTIGO(
        "DELETE FROM testigo"
    ),
    DELETE_USUARIO(
        "DELETE FROM usuario"
    ),
    
    
    
    
   
/*******************************************************************************/    
   
    
    
    
    
    SELECT_ACOMPAÑANTE(
        "SELECT * FROM acompañante"
    ),
    SELECT_AUDIO(
        "SELECT * FROM audio"
    ),
    SELECT_AUDIOXCOMPARECENCIA(
        "SELECT * FROM audioxcomparecenca"
    ),
    SELECT_CASO(
        "SELECT * FROM catalogocaso"
    ),
    SELECT_OFICINA(
        "SELECT * FROM catalogooficina"
    ),
    SELECT_PUESTO(
        "SELECT * FROM catalogopuesto"
    ),
    SELECT_REGION(
        "SELECT * FROM catalogoregion"
    ),
    SELECT_COMPARECENCIA(
        "SELECT * FROM comparecencia"
    ),
    SELECT_GESTIONADO(
        "SELECT * FROM gestionado"
    ),
    SELECT_GESTIONANTE(
        "SELECT * FROM gestionante"
    ),
    SELECT_INSPECTOR(
        "SELECT * FROM inspector"
    ),
    SELECT_INSPECTORXCOMPARECENCIA(
        "SELECT * FROM inspectorxcomparecencia"
    ),
    SELECT_PERSONA(
        "SELECT * FROM persona"
    ),
    SELECT_PERSONAJURIDICA(
        "SELECT * FROM personajuridica"
    ),
    SELECT_REPRESENTANTE(
        "SELECT * FROM representante"
    ),
    SELECT_TESTIGO(
        "SELECT * FROM testigo"
    ),
    SELECT_USUARIO(
        "SELECT * FROM usuario"
    ),
    




    
/*******************************************************************************/    
    
    
    
    
    
    //ATENCION: AL CONSTRUIR Y EMBELLEZER EL STRING DE LA SENTENCIA
    //VIGILAR QUE SE INTRODUZCAN LOS ESPACIOS.
    
    CHECK_CEDULA(
        "SELECT idPersona FROM persona WHERE cedula = ?"
    ),
    CHECK_EMAIL(
        "SELECT idPersona FROM usuario WHERE email = ?"
    ),
    CHECK_CEDULA_JURIDICA(
        "SELECT idPersonaJuridica FROM personaJuridica WHERE cedulaJuridica = ?"
    ),
    VERIFY_USER(
        "SELECT usr.idPersona FROM usuario AS usr"
                + " JOIN persona AS per ON usr.idPersona = per.idPersona"
                + " WHERE per.cedula = ? AND usr.contraseña = ?"
    ),
    



    
/*******************************************************************************/    

    
    
    
    
    GET_ID_PERSONA(
        "SELECT idPersona FROM persona WHERE cedula = ?"
    ),
    GET_ID_PERSONAJURIDICA(
        "SELECT idPersonaJuridica FROM personaJuridica WHERE cedulaJuridica = ?"
    ),
    GET_ID_USUARIO(
        "SELECT usr.idPersona FROM usuario AS usr"
                + " JOIN persona AS per ON usr.idUsuario = per.idPersona"
                + " WHERE per.cedula = ?"
    ),
    GET_ID_INSPECTOR(
        "SELECT ins.idPersona FROM inspector AS ins"
                + " JOIN persona AS per ON ins.idPersona = per.idPersona"
                + " WHERE per.cedula = ?"
    ),
    GET_ID_COMPARECENCIA(
        "SELECT idComparecencia FROM comparecencia WHERE codigoSILAC = ?"
    ),
    GET_ID_AUDIO(
        "SELECT idAudio FROM audio WHERE nombreAudio = ?"
    ),
    GET_ID_GESTIONANTE(
        "SELECT ges.idGestionante FROM gestionante AS ges"
                + " JOIN persona AS per ON ges.idGestionante = per.idPersona"
                + " WHERE per.cedula = ?"    
    ),
    GET_ID_GESTIONANTE_J(
        "SELECT ges.idGestionante FROM gestionante AS ges"
                + " JOIN personajuridica AS perj ON ges.idPersonaJuridca = perj.idPersonaJuridica"
                + " WHERE perj.cedulaJuridica = ?"    
    ),
    GET_ID_GESTIONADO(
        "SELECT ges.idGestionado FROM gestionado AS ges"
                + " JOIN persona AS per ON ges.idPersona = ges.idPersona"
                + " WHERE per.cedula = ?"    
    ),
    GET_ID_GESTIONADO_J(
        "SELECT ges.idGestionado FROM gestionado AS ges"
                + " JOIN personajuridica AS perj ON ges.idPersonaJuridica = perj.idPersonaJuridica"
                + " WHERE perj.cedula = ?"    
    ),
    GET_ID_TESTIGO(
        "SELECT tes.idPersona FROM testigo AS tes"
                + " JOIN persona AS per ON tes.idPersona = per.idPersona"
                + " WHERE per.cedula = ?"    
    ),
    GET_ID_ACOMPAÑANTE(
        "SELECT aco.idPersona FROM acompañante AS aco"
                + " JOIN persona AS per ON aco.idPersona = per.idPersona"
                + " WHERE per.cedula = ?"    
    ),
    GET_ID_REPRESENTANTE(
        "SELECT rep.idPersona FROM representante AS rep"
                + " JOIN persona AS per ON rep.idPersona = per.idPersona"
                + " WHERE per.cedula = ?"    
    ),

    
/*******************************************************************************/    
    
    ID_LAST_ROW_ACOMPAÑANTE(
        "SELECT MAX(idPersona) FROM acompañante"
    ),
    ID_LAST_ROW_AUDIO(
        "SELECT MAX(idAudio) FROM audio"
    ),
    ID_LAST_ROW_COMPARECENCIA(
        "SELECT MAX(idComparecencia) FROM comparecencia"
    ),
    ID_LAST_ROW_GESTIONADO(
        "SELECT MAX(IdGestionado) FROM gestionado"
    ),
    ID_LAST_ROW_GESTIONANTE(
        "SELECT MAX(IdGestionante) FROM gestionante"
    ),
    ID_LAST_ROW_INSPECTOR(
        "SELECT MAX(idPersona) FROM inspector"
    ),
    ID_LAST_ROW_PERSONA(
        "SELECT MAX(idPersona) FROM persona"
    ),
    ID_LAST_ROW_PERSONAJURIDICA(
        "SELECT MAX(idPersonaJuridica) FROM personaJuridica"
    ),
    ID_LAST_ROW_REPRESENTANTE(
        "SELECT MAX(idPersona) FROM representante"
    ),
    ID_LAST_ROW_TESTIGO(
        "SELECT MAX(idPersona) FROM testigo"
    ),
    ID_LAST_ROW_USUARIO(
        "SELECT MAX(idPersona) FROM usuario"
    ),
    
/*******************************************************************************/    

    GET_AUDIO_RELATED_COMPARECENCIA(
        """
        SELECT au.idAudio FROM audio AS au
        JOIN audioxcomparecencia AS axc ON au.idAudio = axc.idAudio 
        JOIN comparecencia AS comp ON comp.idComparecencia
            WHERE comp.idComparecencia = ?
        AND axc.idComparecencia = ?
        """
    ),
    GET_GESTIONANTE_RELATED_COMPARECENCIA(
      """
      SELECT gnte.idGestionante FROM gestionante AS gnte
      JOIN comparecencia AS comp ON comp.idComparecencia = gnte.idComparecencia
      WHERE comp.idComparecencia = ?
      """      
    ),
    GET_GESTIONADO_RELATED_COMPARECENCIA(
      """
      SELECT gndo.idGestionado FROM gestionado AS gndo
      JOIN comparecencia AS comp ON comp.idComparecencia = gndo.idComparecencia
      WHERE comp.idComparecencia = ?
      """      
    ),
    GET_TESTIGO_RELATED_GESTIONANTE(
      """
      SELECT tes.idPersona FROM testigo AS tes
      JOIN gestionante AS gnte ON gnte.idGestionante = tes.idGestionante
      WHERE tes.idGestionante = ?
      """      
    ),
    GET_REPRESENTANTE_RELATED_GESTIONANTE(
      """
      SELECT rep.idPersona FROM representante AS rep
      JOIN gestionante AS gnte ON gnte.idGestionante = rep.idGestionante
      WHERE rep.idGestionante = ?
      """      
    ),
    GET_ACOMPAÑANTE_RELATED_GESTIONANTE(
      """
      SELECT aco.idPersona FROM acompañante AS aco
      JOIN gestionante AS gnte ON gnte.idGestionante = aco.idGestionante
      WHERE aco.idGestionante = ?
      """      
    ),
    GET_TESTIGO_RELATED_GESTIONADO(
      """
      SELECT tes.idPersona FROM testigo AS tes
      JOIN gestionado AS gndo ON gndo.idGestionado = tes.idGestionado
      WHERE tes.idGestionado = ?
      """      
    ),
    GET_REPRESENTANTE_RELATED_GESTIONADO(
      """
      SELECT rep.idPersona FROM representante AS rep
      JOIN gestionado AS gndo ON gndo.idGestionado = rep.idGestionado
      WHERE rep.idGestionado = ?
      """      
    ),
    GET_ACOMPAÑANTE_RELATED_GESTIONADO(
      """
      SELECT aco.idPersona FROM acompañante AS aco
      JOIN gestionado AS gndo ON gndo.idGestionado = aco.idGestionado
      WHERE aco.idGestionado = ?
      """      
    ),
    GET_DATOS_PANTALLA_PRINCIPAL(
        "SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha"
			+ " FROM comparecencia AS comp"
			+ " JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso"
			+ " JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia"
			+ " JOIN audio AS au ON axc.idAudio = au.idAudio"
                        + " GROUP BY comp.codigoSILAC"
    ),
    GET_DATOS_PANTALLA_PRINCIPAL_NO_AUDIO(
         """
        SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
                                         FROM comparecencia AS comp
                                         JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso
                                         LEFT JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia
                                         LEFT JOIN audio AS au ON axc.idAudio = au.idAudio
                                         WHERE au.idAudio IS NULL OR axc.idAudio IS NULL
                                         GROUP BY comp.codigoSILAC
         """
    ),
    BUSQUEDA_DATOS_TABLA(
        "SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha"
                    + " FROM comparecencia AS comp"
                    + " JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso"
                    + " JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia"
                    + " JOIN audio AS au ON axc.idAudio = au.idAudio"
                    + " WHERE ([A] LIKE "+"\"%"+"[B]"+"%\")"
                    + " AND (comp.fecha BETWEEN \"[C]\" AND \"[D]\")"
                    + " GROUP BY comp.codigoSILAC"
                    + " ORDER BY [A] [E]"
    ),
    GET_PATHS_ANOTACIONES(
        """
        SELECT pathArchivoAnotaciones FROM audio
        """        
    ),
    BUSQUEDA_ANOTACIONES(
     """
        SELECT comp.codigoSILAC, au.nombreAudio, cat.tipoCaso, comp.ubicacion, comp.fecha
            FROM comparecencia AS comp
            JOIN catalogocaso AS cat ON comp.idCaso = cat.idCaso
            JOIN audioxcomparecencia AS axc ON comp.idComparecencia = axc.idComparecencia
            JOIN audio AS au ON axc.idAudio = au.idAudio
            WHERE au.pathArchivoAnotaciones = ?
            GROUP BY comp.codigoSILAC
     """       
    ),
    
    
/*******************************************************************************/    
   
    
    
    
    CREATE_OBJECT_ACOMPAÑANTE(
        "SELECT * FROM acompañante WHERE idPersona = ?"
    ),
    CREATE_OBJECT_AUDIO(
        "SELECT * FROM audio WHERE idAudio = ?"
    ),
    CREATE_OBJECT_COMPARECENCIA(
        "SELECT * FROM comparecencia WHERE idComparecencia = ?"
    ),
    CREATE_OBJECT_GESTIONADO(
        "SELECT * FROM gestionado WHERE idGestionado = ?"
    ),
    CREATE_OBJECT_GESTIONANTE(
        "SELECT * FROM gestionante WHERE idGestionante = ?"
    ),    
    CREATE_OBJECT_INSPECTOR(
        "SELECT * FROM inspector WHERE idPersona = ?"   
    ),
    CREATE_OBJECT_PERSONA(
        "SELECT * FROM persona WHERE idPersona = ?"
    ),
    CREATE_OBJECT_PERSONA_JURIDICA(
        "SELECT * FROM personaJuridica WHERE idPersonaJuridica = ?"
    ),
    CREATE_OBJECT_REPRESENTANTE(
        "SELECT * FROM representante WHERE idPersona = ?"
    ),
    CREATE_OBJECT_TESTIGO(
        "SELECT * FROM testigo WHERE idPersona = ?"
    ),
    CREATE_OBJECT_USUARIO(
        "SELECT * FROM usuario WHERE idPersona = ?"
    ),
    
    
    
    

    
/*******************************************************************************/    
    
    
    LOGIN(
        "UPDATE usuario SET enLinea = TRUE WHERE (usuario.idPersona) = ?"
    ),
    LOGOUT(
        "UPDATE usuario SET enLinea = FALSE WHERE (usuario.idPersona) = ?"
    ),
    
    
/*******************************************************************************/    
    



//EXPORTAR E IMPORTAR DATOS        
/*******************************************************************************/    
    
    ATTACH_DATADB(
        """
        ATTACH DATABASE '[DB]' AS 'expDB';
        """    
    ),
    EXPORT_COMPARECENCIAS(
        """
        INSERT INTO expDB.comparecencia 
        SELECT * FROM main.comparecencia 
        WHERE main.comparecencia.idComparecencia = ?
        """
    ),
    EXPORT_AUDIOXCOMPARECENCIAS(
        """
        INSERT INTO expDB.audioxcomparecencia 
        SELECT * FROM main.audioxcomparecencia  
        WHERE main.audioxcomparecencia.idComparecencia = ?
        """
    ),
    EXPORT_AUDIO(
        """
        INSERT INTO expDB.audio
        SELECT * FROM main.audio
        WHERE main.audio.idAudio = ?
        """
    ),
    EXPORT_GESTIONANTE(
        """
        INSERT INTO expDB.gestionante
        SELECT * FROM main.gestionante
        WHERE main.gestionante.idComparecencia = ?
        """
    ),
    EXPORT_PERSONA(
        """
        INSERT INTO expDB.persona
        SELECT * FROM main.persona
        WHERE main.persona.idPersona = ?
        """
    ),
    EXPORT_PERSONA_JURIDICA(
        """
        INSERT INTO expDB.personajuridica
        SELECT * FROM main.personajuridica
        WHERE main.personajuridica.idPersonaJuridica = ?
        """
    ),
    EXPORT_TESTIGO_GESTIONANTE(
        """
        INSERT INTO expDB.testigo
        SELECT * FROM main.testigo
        WHERE main.testigo.idGestionante = ?
        """
    ),
    EXPORT_ACOMPAÑANTE_GESTIONANTE(
        """
        INSERT INTO expDB.acompañante
        SELECT * FROM main.acompañante
        WHERE main.acompañante.idGestionante = ?
        """
    ),
    EXPORT_REPRESENTANTE_GESTIONANTE(
        """
        INSERT INTO expDB.representante
        SELECT * FROM main.representante
        WHERE main.representante.idGestionante = ?
        """
    ),
    EXPORT_GESTIONADO(
        """
        INSERT INTO expDB.gestionado
        SELECT * FROM main.gestionado
        WHERE main.gestionado.idComparecencia = ?
        """
    ),
    EXPORT_TESTIGO_GESTIONADO(
        """
        INSERT INTO expDB.testigo
        SELECT * FROM main.testigo
        WHERE main.testigo.idGestionado = ?
        """
    ),
    EXPORT_ACOMPAÑANTE_GESTIONADO(
        """
        INSERT INTO expDB.acompañante
        SELECT * FROM main.acompañante
        WHERE main.acompañante.idGestionado = ?
        """
    ),
    EXPORT_REPRESENTANTE_GESTIONADO(
        """
        INSERT INTO expDB.representante
        SELECT * FROM main.representante
        WHERE main.representante.idGestionado = ?
        """
    ),
    EXPORT_INSPECTORXCOMPARECENCIAS(
        """
        INSERT INTO expDB.inspectorxcomparecencia 
        SELECT * FROM main.inspectorxcomparecencia  
        WHERE main.inspectorxcomparecencia.idComparecencia = ?
        """
    ),
    EXPORT_INSPECTOR(
        """
        INSERT INTO expDB.inspector
        SELECT * FROM main.inspector
        WHERE main.inspector.idPersona = ?         
        """
    ),
    EXPORT_USUARIO(
        """
        INSERT INTO expDB.usuario 
        SELECT * FROM main.usuario
        WHERE main.usuario.idPersona = ?
        """
    ),
 


/*******************************************************************************/    

    
    
    IMPORT_PERSONA(
        """
        INSERT INTO main.persona SELECT * FROM expDB.persona WHERE ROWID = ?
        """
    ),
    IMPORT_PERSONA_JURIDICA(
        """
        INSERT INTO main.personajuridica SELECT * FROM expDB.personajuridica WHERE ROWID = ?
        """
    ),
    IMPORT_USUARIO(
        """
        INSERT INTO main.usuario SELECT * FROM expDB.usuario
        """
    ),
    IMPORT_INSPECTOR(
        """
        INSERT INTO main.inspector SELECT * FROM expDB.inspector
        """
    ),
    IMPORT_COMPARECENCIA(
         """
         INSERT INTO main.comparecencia SELECT * FROM expDB.comparecencia WHERE ROWID = ?
         """
    ),
    IMPORT_AUDIO(
        """
        INSERT INTO main.audio SELECT * FROM expDB.audio WHERE ROWID = ?
        """
    ),
    IMPORT_INSPECTORXCOMPARECENCIAS(
        """
        INSERT INTO main.inspectorxcomparecencia SELECT * FROM expDB.inspectorxcomparecencia
        """
    ),
    IMPORT_AUDIOXCOMPARECENCIAS(
        """
        INSERT INTO main.audioxcomparecencia SELECT * FROM expDB.audioxcomparecencia
        """
    ),
    IMPORT_GESTIONADO(
        """
        INSERT INTO main.gestionado SELECT * FROM expDB.gestionado WHERE ROWID = ?
        """
    ),    
    IMPORT_GESTIONANTE(
        """
        INSERT INTO main.gestionante SELECT * FROM expDB.gestionante WHERE ROWID = ?
        """
    ),
    IMPORT_TESTIGO(
        """
        INSERT INTO main.testigo SELECT * FROM expDB.testigo
        """
    ),
    IMPORT_ACOMPAÑANTE(
        """
        INSERT INTO main.acompañante SELECT * FROM expDB.acompañante
        """
    ),
    IMPORT_REPRESENTANTE(
        """
        INSERT INTO main.representante SELECT * FROM expDB.representante
        """
    ),

    
    
    GET_NUMBER_ROWS_PERSONA(
        """
        SELECT COUNT(ROWID) FROM expDB.persona
        """
    ),
    GET_NUMBER_ROWS_PERSONA_JURIDICA(
        """
        SELECT COUNT(ROWID) FROM expDB.personajuridica
        """
    ),
    GET_NUMBER_ROWS_USUARIO(
        """
        SELECT COUNT(ROWID) FROM expDB.usuario
        """
    ),
    GET_NUMBER_ROWS_INSPECTOR(
        """
        SELECT COUNT(ROWID) FROM expDB.inspector
        """
    ),
    GET_NUMBER_ROWS_COMPARECENCIA(
        """
        SELECT COUNT(ROWID) FROM expDB.comparecencia
        """
    ),
    GET_NUMBER_ROWS_AUDIO(
        """
        SELECT COUNT(ROWID) FROM expDB.audio
        """
    ),
    GET_NUMBER_ROWS_IXC(
        """
        SELECT COUNT(ROWID) FROM expDB.inspectorxcomparecencia
        """
    ),
    GET_NUMBER_ROWS_AXC(
        """
        SELECT COUNT(ROWID) FROM expDB.audioxcomparecencia
        """
    ),
    GET_NUMBER_ROWS_GESTIONADO(
        """
        SELECT COUNT(ROWID) FROM expDB.gestionado
        """
    ),
    GET_NUMBER_ROWS_GESTIONANTE(
        """
        SELECT COUNT(ROWID) FROM expDB.gestionante
        """
    ),
    GET_NUMBER_ROWS_TESTIGO(
        """
        SELECT COUNT(ROWID) FROM expDB.testigo
        """
    ),
    GET_NUMBER_ROWS_ACOMPAÑANTE(
        """
        SELECT COUNT(ROWID) FROM expDB.acompañante
        """
    ),
    GET_NUMBER_ROWS_REPRESENTANTE(
        """
        SELECT COUNT(ROWID) FROM expDB.representante
        """
    ),
    
    
    NULL_ID_PERSONA(
        """
        UPDATE expDB.persona SET idPersona = NULL WHERE ROWID = ?
        """
    ),
    NULL_ID_PERSONA_JURIDICA(
        """
        UPDATE expDB.personajuridica SET idPersonaJuridica = NULL WHERE ROWID = ?
        """
    ),
    NULL_ID_COMPARECENCIA(
        """
        UPDATE expDB.comparecencia SET idComparecencia = NULL WHERE ROWID = ?
        """
    ),
    NULL_ID_AUDIO(
        """
        UPDATE expDB.audio SET idAudio = NULL WHERE ROWID = ?
        """
    ),
    NULL_ID_GESTIONADO(
        """
        UPDATE expDB.gestionado SET idGestionado = NULL WHERE ROWID = ?
        """
    ),
    NULL_ID_GESTIONANTE(
        """
        UPDATE expDB.gestionante SET idGestionante = NULL WHERE ROWID = ?
        """
    ),

    
    
    
    GET_ID_PERSONA_BYROW(
        """
        SELECT idPersona FROM expDB.persona WHERE ROWID = ?
        """
    ),
    GET_ID_PERSONA_JURIDICA_BYROW(
        """
        SELECT idPersonaJuridica FROM expDB.personajuridica WHERE ROWID = ?
        """
    ),
    GET_ID_COMPARECENCIA_BYROW(
        """
        SELECT idComparecencia FROM expDB.comparecencia WHERE ROWID = ?
        """
    ),
    GET_ID_AUDIO_BYROW(
        """
        SELECT idAudio FROM expDB.audio WHERE ROWID = ?
        """
    ),
    GET_ID_GESTIONADO_BYROW(
        """
        SELECT idGestionado FROM expDB.gestionado WHERE ROWID = ?
        """
    ),
    GET_ID_GESTIONANTE_BYROW(
        """
        SELECT idGestionante FROM expDB.gestionante WHERE ROWID = ?
        """
    ),
    
    
    
    
    UPDATE_DURACION_AUDIO(
        """
        UPDATE audio SET duracionAudio = ? WHERE idAudio = ?
        """        
    ),
        
        
    
    
    
    //PRINCIPAL
    UPDATE_ID_PERSONA_PERSONA(
        ///MAX = GET LAST INSERT TO MAIN
        //ROWID = GET CURRENT INSERT FROM EXP
        """
        UPDATE expDB.persona 
        SET idPersona =
        (
          SELECT MAX(idPersona)
          FROM main.persona
        )
        WHERE ROWID = ?
        """
    ),//DEPENDIENTES
    UPDATE_ID_PERSONA_USUARIO(
        //ROWID? = GET NEW ID OF THE CURRENT INSERT
        //idPersona? = OLD VALUE TO BE REPLACED
        """
        UPDATE expDB.usuario
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_INSPECTOR(
        """
        UPDATE expDB.inspector
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_INSPECTORXCOMPARECENCIAS(
        """
        UPDATE expDB.inspectorxcomparecencia
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_GESTIONADO(
        """
        UPDATE expDB.gestionado
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_GESTIONANTE(
        """
        UPDATE expDB.gestionante
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_TESTIGO(
        """
        UPDATE expDB.testigo
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_ACOMPAÑANTE(
        """
        UPDATE expDB.acompañante
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    UPDATE_ID_PERSONA_REPRESENTANTE(
        """
        UPDATE expDB.representante
        SET idPersona =
        (
          SELECT idPersona
          FROM expDB.persona
          WHERE ROWID = ?
        )
        WHERE idPersona = ?
        """
    ),
    
    
    
    
    
    //PRINCIPAL
    UPDATE_ID_PERSONA_JURIDICA(
        """
        UPDATE expDB.personajuridica
        SET idPersonaJuridica =
        (
          SELECT MAX(idPersonaJuridica)
          FROM main.personajuridica
        )
        WHERE ROWID = ?
        """
    ),//DEPENDIENTES
    UPDATE_ID_PERSONA_JURIDICA_GESTIONADO(
        """
        UPDATE expDB.gestionado
        SET idPersonaJuridica =
        (
          SELECT idPersonaJuridica
          FROM expDB.personajuridica
          WHERE ROWID = ?
        
        )
        WHERE idPersonaJuridica = ?
        """
    ),
    UPDATE_ID_PERSONA_JURIDICA_GESTIONANTE(
        """
        UPDATE expDB.gestionante
        SET idPersonaJuridica =
        (
          SELECT idPersonaJuridica
          FROM expDB.personajuridica
          WHERE ROWID = ?
        )
        WHERE idPersonaJuridica = ?
        """
    ),
    
    
    
    
    
    //PRINCIPAL
    UPDATE_ID_COMPARECENCIA(
        """
        UPDATE expDB.comparecencia
        SET idComparecencia =
        (
          SELECT MAX(idComparecencia)
          FROM main.comparecencia
        )
        WHERE ROWID = ?
        """
    ),//DEPENDIENTES
    UPDATE_ID_COMPARECENCIA_GESTIONADO(
        """
        UPDATE expDB.gestionado
        SET idComparecencia =
        (
          SELECT idComparecencia
          FROM expDB.comparecencia
          WHERE ROWID = ?
        )
        WHERE idComparecencia = ?
        """
    ),
    UPDATE_ID_COMPARECENCIA_GESTIONANTE(
        """
        UPDATE expDB.gestionante
        SET idComparecencia =
        (
          SELECT idComparecencia
          FROM expDB.comparecencia
          WHERE ROWID = ?
        )
        WHERE idComparecencia = ?
        """
    ),
    UPDATE_ID_COMPARECENCIA_AXC(
        """
        UPDATE expDB.audioxcomparecencia
        SET idComparecencia =
        (
          SELECT idComparecencia
          FROM expDB.comparecencia
          WHERE ROWID = ?
        )
        WHERE idComparecencia = ?
        """
    ),
    UPDATE_ID_COMPARECENCIA_IXC(
        """
        UPDATE expDB.inspectorxcomparecencia
        SET idComparecencia =
        (
          SELECT idComparecencia
          FROM expDB.comparecencia
          WHERE ROWID = ?
        )
        WHERE idComparecencia = ?
        """
    ),
    
    
    //PRINCIPAL
    UPDATE_ID_AUDIO(
        """
        UPDATE expDB.audio
        SET idAudio =
        (
          SELECT MAX(idAudio)
          FROM main.audio
        )
        WHERE ROWID = ?
        """
    ),//DEPENDIENTES
    UPDATE_ID_AUDIO_AXC(
        """
        UPDATE expDB.audioxcomparecencia
        SET idAudio =
        (
          SELECT idAudio
          FROM expDB.audio
          WHERE ROWID = ?
        )
        WHERE idAudio = ?
        """
    ),
    UPDATE_AUDIO_PATH(
        """
        UPDATE expDB.audio SET pathArchivoAudio = ? WHERE ROWID = ?
        """  
    ),
    GET_AUDIO_PATH_BYROW(
        """
        SELECT pathArchivoAudio FROM expDB.audio WHERE ROWID = ?
        """
    ),
    UPDATE_ANOTACIONES_PATH(
        """
        UPDATE expDB.audio SET pathArchivoAnotaciones = ? WHERE ROWID = ?
        """
    ),
    GET_ANOTACIONES_PATH_BYROW(
        """
        SELECT pathArchivoAnotaciones FROM expDB.audio WHERE ROWID = ?
        """
    ),

    
    
    
    
    //PRINCIPAL
    UPDATE_ID_GESTIONADO(
        """
        UPDATE expDB.gestionado
        SET idGestionado =
        (
          SELECT MAX(idGestionado)
          FROM main.gestionado
        )
        WHERE ROWID = ?
        """
    ),//DEPENDIENTES
    UPDATE_ID_GESTIONADO_TESTIGO(
        """
        UPDATE expDB.testigo
        SET idGestionado =
        (
          SELECT idGestionado
          FROM expDB.gestionado
          WHERE ROWID = ?
        
        )
        WHERE idGestionado = ?
        """
    ),
    UPDATE_ID_GESTIONADO_ACOMPAÑANTE(
        """
        UPDATE expDB.acompañante
        SET idGestionado =
        (
          SELECT idGestionado
          FROM expDB.gestionado
          WHERE ROWID = ?

        )
        WHERE idGestionado = ?
        """
    ),
    UPDATE_ID_GESTIONADO_REPRESENTANTE(
        """
        UPDATE expDB.representante
        SET idGestionado =
        (
          SELECT idGestionado
          FROM expDB.gestionado
          WHERE ROWID = ?

        )
        WHERE idGestionado = ?
        """
    ),
    
    
    
    //PRINCIPAL
    UPDATE_ID_GESTIONANTE(
        """
        UPDATE expDB.gestionante
        SET idGestionante =
        (
          SELECT MAX(idGestionante)
          FROM main.gestionante
        )
        WHERE ROWID = ?
        """
    ),//DEPENDIENTES
    UPDATE_ID_GESTIONANTE_TESTIGO(
        """
        UPDATE expDB.testigo
        SET idGestionante =
        (
          SELECT idGestionante
          FROM expDB.gestionante
          WHERE ROWID = ?
        )
        WHERE idGestionante = ?
        """
    ),
    UPDATE_ID_GESTIONANTE_ACOMPAÑANTE(
        """
        UPDATE expDB.acompañante
        SET idGestionante =
        (
          SELECT idGestionante
          FROM expDB.gestionante
          WHERE ROWID = ?
        )
        WHERE idGestionante = ?
        """
    ),
    UPDATE_ID_GESTIONANTE_REPRESENTANTE(
        """
        UPDATE expDB.representante
        SET idGestionante =
        (
          SELECT idGestionante
          FROM expDB.gestionante
          WHERE ROWID = ?
        )
        WHERE idGestionante = ?
        """
    ),
    
    
//SUBIR Y DESCARGAR DATOS
/*******************************************************************************/    
    UPLOAD_COMPARECENCIAS(
        """
        SELECT * FROM comparecencia 
        WHERE comparecencia.idComparecencia = ?
        """
    ),
    UPLOAD_AUDIOXCOMPARECENCIAS(
        """
        SELECT * FROM audioxcomparecencia  
        WHERE audioxcomparecencia.idComparecencia = ?
        """
    ),
    UPLOAD_AUDIO(
        """
        SELECT * FROM audio
        WHERE audio.idAudio = ?
        """
    ),
    UPLOAD_GESTIONANTE(
        """
        SELECT * FROM gestionante
        WHERE gestionante.idComparecencia = ?
        """
    ),
    UPLOAD_PERSONA(
        """
        SELECT * FROM persona
        WHERE persona.idPersona = ?
        """
    ),
    UPLOAD_PERSONA_JURIDICA(
        """
        SELECT * FROM personajuridica
        WHERE personajuridica.idPersonaJuridica = ?
        """
    ),
    UPLOAD_TESTIGO_GESTIONANTE(
        """
        SELECT * FROM testigo
        WHERE testigo.idGestionante = ?
        """
    ),
    UPLOAD_ACOMPAÑANTE_GESTIONANTE(
        """
        SELECT * FROM acompañante
        WHERE acompañante.idGestionante = ?
        """
    ),
    UPLOAD_REPRESENTANTE_GESTIONANTE(
        """
        SELECT * FROM representante
        WHERE representante.idGestionante = ?
        """
    ),
    UPLOAD_GESTIONADO(
        """
        SELECT * FROM gestionado
        WHERE gestionado.idComparecencia = ?
        """
    ),
    UPLOAD_TESTIGO_GESTIONADO(
        """
        SELECT * FROM testigo
        WHERE testigo.idGestionado = ?
        """
    ),
    UPLOAD_ACOMPAÑANTE_GESTIONADO(
        """
        SELECT * FROM acompañante
        WHERE acompañante.idGestionado = ?
        """
    ),
    UPLOAD_REPRESENTANTE_GESTIONADO(
        """
        SELECT * FROM representante
        WHERE representante.idGestionado = ?
        """
    ),
    UPLOAD_INSPECTORXCOMPARECENCIAS(
        """
        SELECT * FROM inspectorxcomparecencia  
        WHERE inspectorxcomparecencia.idComparecencia = ?
        """
    ),
    UPLOAD_INSPECTOR(
        """
        SELECT * FROM inspector
        WHERE inspector.idPersona = ?         
        """
    ),
    UPLOAD_USUARIO(
        """
        SELECT * FROM usuario
        WHERE usuario.idPersona = ?
        """
    ),
    
    
    
    //OTROS
    GET_AUDIOPATH_BY_IDS(
        """
        SELECT au.pathArchivoAudio FROM audio AS au
        JOIN audioxcomparecencia AS axc ON au.idAudio = axc.idAudio
        JOIN comparecencia AS comp ON  axc.idComparecencia = comp.idComparecencia
        WHERE comp.idComparecencia = ? AND au.idAudio = ?
    """),
    GET_ANOTACIONPATH_BY_IDS("""
        SELECT au.pathArchivoAnotaciones FROM audio AS au
        JOIN audioxcomparecencia AS axc ON au.idAudio = axc.idAudio
        JOIN comparecencia AS comp ON  axc.idComparecencia = comp.idComparecencia
        WHERE comp.idComparecencia = ? AND au.idAudio = ?                         
    """);
    
    
    private final String display;

    private SQL(String s) {
        display = s;
    }

    @Override
    public String toString() {
        return display;
    } 
}

/*
UNIVERSIDAD ESTATAL A DISTANCIA
VICERRECTORIA ACADÉMICA 
ESCUELA DE CIENCIAS EXACTAS Y NATURALES 
CARRERA INGENIERÍA INFORMÁTICA 

Desarrollar una aplicación de escritorio
Para la administración de comparecencias del
Ministerio de Trabajo y Seguridad Social de la
Región Huetar Caribe

MODALIDAD ESCOGIDA: PROYECTO

PARTE PROGRAMADA
PARA OPTAR POR EL TÍTULO DE 
BACHILLER EN INGENIERÍA INFORMÁTICA 

PROPRIETARIO:
MOISES ROMERO PRADO
CEDULA 303370265

AUTORES:
ROBERT JESÚS CASCANTE ARAYA,
CÉDULA 305180118
CORREO jesuscascantearaya@gmail.com
TELEFONO 88943263
DAYRON ANTONY CHAVES SANDOVAL,
CÉDULA 305240018 
TELEFONO 83959225
CORREO dayron.chaves@pm.me

CENTRO UNIVERSITARIO DE TURRIALBA
PAC 2023-1
TURRIALBA, 2023  
*/