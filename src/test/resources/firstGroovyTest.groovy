import ru.alcereo.usability.meta.Commands_
import ru.alcereo.usability.meta.ProcessorsVersions_

def predicate = Commands_.id greaterThan 3

ProcessorsVersions_.select(qBuilder).where(predicate).getResultList()