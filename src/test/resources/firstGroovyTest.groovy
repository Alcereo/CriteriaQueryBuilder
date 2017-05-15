import tests.entities.meta.Commands_
import tests.entities.meta.ProcessorsVersions_

def predicate = Commands_.id greaterThan 3

ProcessorsVersions_.select(qBuilder).where(predicate).getResultList()