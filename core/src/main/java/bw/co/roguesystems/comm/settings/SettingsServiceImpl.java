// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::settings::SettingsService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.settings;

import java.util.Collection;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.settings.SettingsService
 */
@Service("settingsService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class SettingsServiceImpl
    extends SettingsServiceBase
{
    public SettingsServiceImpl(
        SettingsDao settingsDao,
        SettingsRepository settingsRepository,
        MessageSource messageSource
    ) {
        
        super(
            settingsDao,
            settingsRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#findById(String)
     */
    @Override
    protected SettingsDTO handleFindById(String id)
        throws Exception
    {

        Settings settings = this.settingsRepository.findById(id)
            .orElseThrow(() -> new Exception("Settings not found for id: " + id));

        return this.settingsDao.toSettingsDTO(settings);
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#save(SettingsDTO)
     */
    @Override
    protected SettingsDTO handleSave(SettingsDTO settings)
        throws Exception
    {

        Settings settingsEntity = this.settingsDao.settingsDTOToEntity(settings);
        settingsEntity = this.settingsRepository.save(settingsEntity);
        return this.settingsDao.toSettingsDTO(settingsEntity);
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {

        this.settingsRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#getAll()
     */
    @Override
    protected Collection<SettingsDTO> handleGetAll()
        throws Exception
    {

        Collection<Settings> settings = this.settingsRepository.findAll();
        return this.settingsDao.toSettingsDTOCollection(settings);
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#search(String)
     */
    @Override
    protected Collection<SettingsDTO> handleSearch(String criteria)
        throws Exception
    {

        // TODO implement protected  Collection<SettingsDTO> handleSearch(String criteria) 
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.settings.SettingsService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#getAll(Integer, Integer)
     */
    @Override
    protected Page<SettingsDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Settings> settings = this.settingsRepository.findAll(pageRequest);
        return settings.map(setting -> this.settingsDao.toSettingsDTO(setting));
    }

    /**
     * @see bw.co.roguesystems.comm.settings.SettingsService#search(String, Integer, Integer)
     */
    @Override
    protected Page<SettingsDTO> handleSearch(String criteria, Integer pageNumber, Integer pageSize)
        throws Exception
    {
        // TODO implement protected  Page<SettingsDTO> handleSearch(String criteria, Integer pageNumber, Integer pageSize)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.settings.SettingsService.handleSearch(String criteria, Integer pageNumber, Integer pageSize) Not implemented!");
    }

}